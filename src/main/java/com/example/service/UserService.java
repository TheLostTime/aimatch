package com.example.service;

import com.example.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    void registerUser(User user);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    void updateLastLoginTime(Long id);
    String login(User user);
}    