package com.gucardev.postsharingbe.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketStarter implements CommandLineRunner {


    private final SocketIOServer server;

    public SocketStarter(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
