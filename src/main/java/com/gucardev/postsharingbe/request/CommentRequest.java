package com.gucardev.postsharingbe.request;

import com.gucardev.postsharingbe.model.Comment;
import com.gucardev.postsharingbe.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequest {

    private Post post;
    private Comment comment;

}
