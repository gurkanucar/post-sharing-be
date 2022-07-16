package com.gucardev.postsharingbe.controller;

import com.gucardev.postsharingbe.model.Notification;
import com.gucardev.postsharingbe.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/push-notifications")
@Slf4j
public class PushNotificationController {

    private final PushNotificationService service;

    public PushNotificationController(PushNotificationService service) {
        this.service = service;
    }

    @GetMapping("/{userID}")
    public Flux<ServerSentEvent<List<Notification>>> streamLastMessage(@PathVariable String userID) {
        return service.getNotificationsByUserToID(userID);
    }

}
