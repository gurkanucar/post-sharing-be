package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.Post;
import com.gucardev.postsharingbe.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Optional<Post> findPostById(String id);


}
