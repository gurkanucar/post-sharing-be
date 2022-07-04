package com.gucardev.postsharingbe.model.post;

public class PostFactory {
    public Post createPost(PostType type) {
        Post post = null;
        switch (type) {
            case TEXT:
                post = new PostWithText();
                break;
            case IMAGE:
                post = new PostWithImage();
                break;
            case VIDEO:
                post = new PostWithVideo();
                break;
        }
        return post;
    }
}
