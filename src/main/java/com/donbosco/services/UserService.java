package com.donbosco.services;

import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }


    public Optional<User> getUserById(Long id) {
        return iUserRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
        if (iUserRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }
        if (iUserRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        return iUserRepository.save(user);
    }

    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users.", e);
        }
    }
    public User updateUser(User id, long userDetails) {
        User user = iUserRepository.findById(id.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

    public Optional<User> getUserById (int id) {
        try {
            return userRepository.findById(Long.valueOf(id));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving donation details.", e);
        }
    }

    public void updateUser(User user, int Id) {
        user.setId(Id);
        userRepository.save(user);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        return iUserRepository.save(user);
    }

    public boolean deleteUser(int Id) {
        try {
            userRepository.deleteById(Long.valueOf(Id));
            return true;
        } catch (Exception e) {
            return false;
        }
    public boolean deleteUser(Long id) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
        iUserRepository.delete(user);
        return false;
    }
}