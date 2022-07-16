package com.gucardev.postsharingbe.controller;

import com.gucardev.postsharingbe.model.Notification;
import com.gucardev.postsharingbe.service.NotificationStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notification")
@RestController
public class NotificationStorageController {

    private final NotificationStorageService notifService;


    public NotificationStorageController(NotificationStorageService notifService) {
        this.notifService = notifService;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<List<Notification>> getNotificationsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok(notifService.getNotificationsByUserID(userID));
    }

    @PatchMapping("/read/{notifID}")
    public ResponseEntity changeNotifStatusToRead(@PathVariable String notifID) {
        return ResponseEntity.ok(notifService.changeNotifStatusToRead(notifID));
    }


}
