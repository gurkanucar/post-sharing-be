package com.gucardev.postsharingbe.repository;

import com.gucardev.postsharingbe.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationStorageRepository extends MongoRepository<Notification, String> {

    Optional<Notification> findById(String id);

    List<Notification> findByUserToId(String id);


    List<Notification> findByUserToIdAndDeliveredFalse(String id);


}
