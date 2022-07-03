package com.gucardev.postsharingbe.request;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import lombok.Data;

@Data
public class CommentRequest {

    private Post post;
    private Comment comment;

}
