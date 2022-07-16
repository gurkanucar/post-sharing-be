package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found!"));
    }

    public User getUserById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("user not found!"));
    }

    protected boolean isUserContains(final List<User> list, final String username) {
        return list.stream().anyMatch(o -> o.getUsername().equals(username));
    }


}
