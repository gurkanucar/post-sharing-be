package com.gucardev.postsharingbe.controller;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Post> getAllPosts(String postID) {
        return ResponseEntity.ok(postService.getPostByID(postID));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.create(post));
    }

    @PostMapping("/comment")
    public ResponseEntity<Post> addComment(@RequestBody Post post, @RequestBody Comment comment) {
        return ResponseEntity.ok(postService.addComment(post, comment));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Post> removeComment(@RequestBody Post post, @RequestBody Comment comment) {
        return ResponseEntity.ok(postService.removeComment(post, comment));
    }

    @PostMapping("/like")
    public ResponseEntity<Post> addLike(@RequestBody Post post, @RequestBody User user) {
        return ResponseEntity.ok(postService.addLike(post, user));
    }

    @DeleteMapping("/like")
    public ResponseEntity<Post> removeLike(@RequestBody Post post, @RequestBody User user) {
        return ResponseEntity.ok(postService.removeLike(post, user));
    }


}
