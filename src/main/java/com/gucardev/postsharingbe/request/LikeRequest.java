package com.gucardev.postsharingbe.request;

import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import lombok.Data;

@Data
public class LikeRequest {

    private User user;
    private Post post;

}
