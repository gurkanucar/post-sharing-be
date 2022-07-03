package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.NotificationStorage;
import com.gucardev.postsharingbe.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationStorageRepository extends MongoRepository<NotificationStorage, String> {

    Optional<NotificationStorage> findById(String id);

    List<NotificationStorage> findByUserToId(String id);


}
