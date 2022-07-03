package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.repository.PostRepository;
import com.gucardev.postsharingbe.request.CommentRequest;
import com.gucardev.postsharingbe.request.LikeRequest;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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

    public PostService(PostRepository postRepository,
                       UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
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
        return postRepository.findPostById(postID).orElseThrow(() -> new RuntimeException("post not found!"));
    }


    public Post addComment(CommentRequest commentRequest) {
        Post post = commentRequest.getPost();
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
        Post post = commentRequest.getPost();
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
        Post post = likeRequest.getPost();
        User user = likeRequest.getUser();
        // it's checking users at the same time
        User likedUser = userService.getUserByUsername(user.getUsername());
        if (post.getLikedUsers() == null) {
            post.setLikedUsers(new ArrayList<User>());
        }
        post.getLikedUsers().add(likedUser);
        log.info(String.valueOf(post.getLikedUsers().size()));
        return postRepository.save(post);
    }

    public Post removeLike(LikeRequest likeRequest) {
        Post post = likeRequest.getPost();
        User user = likeRequest.getUser();
        // it's checking users at the same time
        User unLikedUser = userService.getUserByUsername(user.getUsername());
        var updatedLikes = post.getLikedUsers()
                .stream()
                .filter(x -> !Objects.equals(x.getId(), unLikedUser.getId()))
                .collect(Collectors.toList());
        post.setLikedUsers(updatedLikes);
        return postRepository.save(post);
    }


}
