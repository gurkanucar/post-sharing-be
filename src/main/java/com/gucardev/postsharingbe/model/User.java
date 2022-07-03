package com.gucardev.postsharingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class User {
    @Id
    private String id;

    @Indexed
    private String username;
    private String profileImageUrl;
    private String name;
    private String surname;

}
