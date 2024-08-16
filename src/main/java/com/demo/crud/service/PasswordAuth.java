package com.demo.crud.service;

import com.demo.crud.entity.UserEntity;
import com.demo.crud.exception.ResourceNotFoundException;
import com.demo.crud.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordAuth {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String rawPassword) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
