package com.gucardev.postsharingbe.controller;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.request.CommentRequest;
import com.gucardev.postsharingbe.request.LikeRequest;
import com.gucardev.postsharingbe.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{postID}")
    public ResponseEntity<Post> getPostById(@PathVariable String postID) {
        return ResponseEntity.ok(postService.getPostByID(postID));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.create(post));
    }

    @PostMapping("/comment")
    public ResponseEntity<Post> addComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(postService.addComment(commentRequest));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Post> removeComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(postService.removeComment(commentRequest));
    }

    @PostMapping("/like")
    public ResponseEntity<Post> addLike(@RequestBody LikeRequest likeRequest) {
        return ResponseEntity.ok(postService.addLike(likeRequest));
    }

    @DeleteMapping("/like")
    public ResponseEntity<Post> removeLike(@RequestBody LikeRequest likeRequest) {
        return ResponseEntity.ok(postService.removeLike(likeRequest));
    }


}
