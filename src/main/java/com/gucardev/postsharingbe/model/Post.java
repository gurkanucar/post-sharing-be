package com.gucardev.postsharingbe.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Post {

    @Id
    private String id;

    private String content;

    private User user;

    private List<User> likedUsers;

    private List<Comment> comments;


    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDate updated;


}
