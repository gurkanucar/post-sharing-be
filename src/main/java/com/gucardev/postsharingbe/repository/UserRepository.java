package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
