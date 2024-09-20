package com.sar.service;

import com.sar.model.User;
import com.sar.model.UserLoginProjection;
import com.sar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }


    public User registerUser(String name, String email, String password, MultipartFile file) throws IOException {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // Consider hashing passwords before storing
        user.setFiledata(file != null ? file.getBytes() : null);

        return userRepository.save(user);
    }


    public boolean authenticateUser(String email, String password) {
        UserLoginProjection user = userRepository.findUserLoginDetailsByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}