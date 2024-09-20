package com.sar.controller;
import com.sar.model.LoginRequest;
import com.sar.model.RegisterRequest;
import com.sar.model.User;
import com.sar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            if (userService.isEmailRegistered(email) ){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
            }else {
                User user = userService.registerUser(name, email, password, file);
                return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to store file");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
