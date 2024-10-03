package com.donbosco.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.donbosco.dto.UserDto;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final IUserRepository userRepository;

    
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(UserDto userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
        throw new RuntimeException("Username already exists.");
    }
    if (userRepository.existsByEmail(userDto.getEmail())) {
        throw new RuntimeException("Email already exists.");
    }

    User user = new User.Builder()
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .email(userDto.getEmail())
        .role(userDto.getRole())
        .build();
        System.out.println(user);
    return userRepository.save(user);
}


    @Override
    public User updateUser(Long id, User userDetails) {
       
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

       
        user = new User.Builder()
                .id(user.getId()) // Mantener el mismo ID
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())  
                .role(userDetails.getRole())
                .reservations(user.getReservations()) 
                .flights(user.getFlights())
                .build();
        return userRepository.save(user);
}


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
        userRepository.delete(user);
    }
}
