package com.gucardev.postsharingbe.request;

import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.model.post.PostType;
import lombok.Data;

@Data
public class CreatePostRequest {

    private String content;
    private User user;
    private PostType type;
    private String imageUrl;
    private String videoUrl;

}
