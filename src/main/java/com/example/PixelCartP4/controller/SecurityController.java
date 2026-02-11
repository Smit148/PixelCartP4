package com.example.PixelCartP4.controller;

// import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.service.UserService;

@Controller
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // your login.html
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; // your register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
            @RequestParam String password) {

        // Create a new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Service will encode it

        // Register user (service handles password encoding and role)
        userService.registerUser(user);

        // Redirect to login page after registration
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }

}