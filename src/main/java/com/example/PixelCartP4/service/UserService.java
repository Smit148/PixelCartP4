package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.User;
import java.util.Optional;

public interface UserService {

    // User Management
    User saveUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    void deleteUser(Long id);

    // Registration
    User registerUser(User user);
}
