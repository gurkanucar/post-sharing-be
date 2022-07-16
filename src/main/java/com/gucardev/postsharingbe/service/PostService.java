package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.*;
import com.gucardev.postsharingbe.repository.PostRepository;
import com.gucardev.postsharingbe.request.CommentRequest;
import com.gucardev.postsharingbe.request.LikeRequest;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;
    private final NotificationStorageService notificationStorageService;


    public PostService(PostRepository postRepository,
                       UserService userService, MongoTemplate mongoTemplate, NotificationStorageService notificationStorageService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mongoTemplate = mongoTemplate;
        this.notificationStorageService = notificationStorageService;
    }

    public Post create(Post post) {
        User user = userService.getUserByUsername(post.getUser().getUsername());
        post.setUser(user);
        post.setComments(new ArrayList<>());
        post.setLikedUsers(new ArrayList<>());
        post.setCreated(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getPostByID(String postID) {
       /* Query query = new Query().addCriteria(Criteria.where("id").is(new ObjectId(postID)));
        log.info(String.valueOf(mongoTemplate.find(query, Post.class)));*/
        return postRepository.findPostById(postID).orElseThrow(() -> new RuntimeException("post not found!"));
    }

    public void deletePostByID(String postID) {
        var post = postRepository.findPostById(postID).orElseThrow(() -> new RuntimeException("post not found!"));
        postRepository.delete(post);
    }


    public Post addComment(CommentRequest commentRequest) {
        Post post = getPostByID(commentRequest.getPost().getId());
        Comment comment = commentRequest.getComment();
        // it's checking users at the same time
        User commentUser = userService.getUserByUsername(comment.getUser().getUsername());
        comment.setUser(commentUser);
        comment.setId(new ObjectId().toString());
        User postUser = userService.getUserByUsername(post.getUser().getUsername());
        post.setUser(postUser);
        post.getComments().add(comment);
        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("new comment from " + commentUser.getUsername())
                .notificationType(NotificationType.COMMENT)
                .userFrom(commentUser)
                .userTo(postUser).build());
        return postRepository.save(post);
    }

    public Post removeComment(CommentRequest commentRequest) {
        Post post = getPostByID(commentRequest.getPost().getId());
        Comment comment = commentRequest.getComment();
        // it's checking users at the same time
        User commentUser = userService.getUserByUsername(comment.getUser().getUsername());
        comment.setUser(commentUser);
        User postUser = userService.getUserByUsername(post.getUser().getUsername());
        post.setUser(postUser);

        var updatedComments = post.getComments()
                .stream()
                .filter(x -> !Objects.equals(x.getId(), comment.getId()))
                .collect(Collectors.toList());
        post.setComments(updatedComments);
        return postRepository.save(post);
    }

    public Post addLike(LikeRequest likeRequest) {
        Post post = getPostByID(likeRequest.getPost().getId());
        User postOfUser = post.getUser();
        // it's checking users at the same time
        User likedUser = userService.getUserByUsername(likeRequest.getUser().getUsername());
        if (post.getLikedUsers() != null && userService.isUserContains(post.getLikedUsers(), likeRequest.getUser().getUsername())) {
            log.info("call remove like: " + likeRequest.getUser().getUsername());
            return removeLike(likeRequest);
        }
        post.getLikedUsers().add(likedUser);
        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("like from " + likedUser.getUsername())
                .notificationType(NotificationType.LIKE)
                .userFrom(likedUser)
                .userTo(postOfUser).build());
        return postRepository.save(post);
    }

    public Post removeLike(LikeRequest likeRequest) {
        log.info("Removelike: " + likeRequest.getUser().getUsername());
        Post post = getPostByID(likeRequest.getPost().getId());
        User requestUser = likeRequest.getUser();
        // it's checking users at the same time
        User unLikedUser = userService.getUserByUsername(requestUser.getUsername());
        var updatedLikes = post.getLikedUsers()
                .stream()
                .filter(x -> !x.getUsername().equals(unLikedUser.getUsername()))
                .collect(Collectors.toList());
        post.setLikedUsers(updatedLikes);
        return postRepository.save(post);
    }


    public List<Comment> getCommentsByPostID(String postID) {
        Post post = getPostByID(postID);
        return post.getComments();
    }

    public Flux<ServerSentEvent<List<Post>>> streamPosts() {
        return Flux.interval(Duration.ofSeconds(2))
                .publishOn(Schedulers.boundedElastic())
                .map(sequence -> ServerSentEvent.<List<Post>>builder().id(String.valueOf(sequence))
                        .event("post-list-event").data(getAll())
                        .build());

    }
}
