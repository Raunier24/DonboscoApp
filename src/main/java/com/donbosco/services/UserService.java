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
        if (iUserRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }
        if (iUserRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        return iUserRepository.save(user);
    }

    public User updateUser(User id, long userDetails) {
        User user = iUserRepository.findById(id.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        
        return iUserRepository.save(user);
    }
    public boolean deleteUser(Long id) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
        iUserRepository.delete(user);
        return false;
    }
}
