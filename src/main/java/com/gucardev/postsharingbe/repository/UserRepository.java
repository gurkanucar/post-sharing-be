package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(String id);
}
