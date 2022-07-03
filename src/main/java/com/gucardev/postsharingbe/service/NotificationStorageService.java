package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.NotificationStorage;
import com.gucardev.postsharingbe.repository.NotificationStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationStorageService {

    private final NotificationStorageRepository notifRepository;

    public NotificationStorageService(NotificationStorageRepository notifRepository) {
        this.notifRepository = notifRepository;
    }

    public NotificationStorage createNotificationStorage(NotificationStorage notificationStorage) {
        return notifRepository.save(notificationStorage);
    }


    public List<NotificationStorage> getNotificationsByUserID(String userID) {
        return notifRepository.findByUserToId(userID);
    }

    public NotificationStorage changeNotifStatusToRead(String notifID) {
        var notif = notifRepository.findById(notifID)
                .orElseThrow(() -> new RuntimeException("not found!"));
        notif.setRead(true);
        return notifRepository.save(notif);
    }
}
