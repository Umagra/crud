package com.demo.crud.service;

import com.demo.crud.entity.UserEntity;
import com.demo.crud.exception.ResourceNotFoundException;
import com.demo.crud.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id)
                .or(() -> { throw new ResourceNotFoundException("User not found with id: " + id); });
    }

    public UserEntity createUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    public Optional<UserEntity> updateUser(Long id, UserEntity userDetails) {
        UserEntity user = userRepository.findById(id).get();
        if(user==null)
            throw new ResourceNotFoundException("User not found with id: " + id);
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // Encrypt password
        user.setActive(userDetails.isActive());
        return Optional.of(userRepository.save(user));

    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
