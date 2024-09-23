package com.donbosco.repositories;

import com.donbosco.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    User createUser(User user);

    List<User> getAllUser();

    Optional<User> getUserById(Long id);

    void updateUser(User userToUpdate, Long id);

    boolean deleteUser(Long id);
}
