package com.donbosco.services;

import java.util.List;
import java.util.Optional;

import com.donbosco.dto.UserDto;
import com.donbosco.models.User;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    User createUser(UserDto userDto);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
}
