package com.gucardev.postsharingbe.request;

import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeRequest {

    private Post post;
    private User user;


}
