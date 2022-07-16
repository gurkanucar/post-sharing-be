package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.repository.PostRepository;
import com.gucardev.postsharingbe.request.CommentRequest;
import com.gucardev.postsharingbe.request.LikeRequest;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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


    public PostService(PostRepository postRepository,
                       UserService userService, MongoTemplate mongoTemplate) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mongoTemplate = mongoTemplate;
    }

    public Post create(Post post) {
        User user = userService.getUserByUsername(post.getUser().getUsername());
        post.setUser(user);
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
        if (post.getComments() == null) {
            post.setComments(new ArrayList<Comment>());
        }
        post.getComments().add(comment);
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
        User requestUser = likeRequest.getUser();
        // it's checking users at the same time
        User likedUser = userService.getUserByUsername(requestUser.getUsername());
        if (post.getLikedUsers() != null && userService.isUserContains(post.getLikedUsers(), requestUser.getUsername())) {
            log.info("call remove like: " + likeRequest.getUser().getUsername());
            return removeLike(likeRequest);
        } else if (post.getLikedUsers() == null) {
            post.setLikedUsers(new ArrayList<User>());
            log.info("create liked users list");
        }
        post.getLikedUsers().add(likedUser);
        log.info(String.valueOf(post.getLikedUsers().size()));
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
}
