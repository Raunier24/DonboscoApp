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

    public User createUser(User user) {
        return iUserRepository.save(user);
    }

    public List<User> getAllUser() {
        try {
            return iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users.", e);
        }
    }

    public Optional<User> getUserById (Long i) {
        try {
            return iUserRepository.findById(Long.valueOf(i));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving donation details.", e);
        }
    }

    public void updateUser(User user, Long Id) {
        user.setId(Id);
        iUserRepository.save(user);
    }

    public boolean deleteUser(Long Id) {
        try {
            iUserRepository.deleteById(Long.valueOf(Id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUserById(int i) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }
}