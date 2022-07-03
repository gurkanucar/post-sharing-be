package com.gucardev.postsharingbe.service;

import com.gucardev.postsharingbe.model.User;
import com.gucardev.postsharingbe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new RuntimeException("user not found!"));
    }

    public User getUserById(String id){
        return userRepository.findUserById(id)
                .orElseThrow(()->new RuntimeException("user not found!"));
    }

}
