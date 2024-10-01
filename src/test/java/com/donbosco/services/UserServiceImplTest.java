package com.donbosco.services;

import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Limpiamos la base de datos antes de cada prueba
        userRepository.deleteAll();

        // Creamos un usuario para las pruebas
        user = new User("testuser", "testpassword", "testuser@example.com", ERole.USER);
        userRepository.save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void testGetUserById() {
        Optional<User> foundUser = userService.getUserById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        Optional<User> foundUser = userService.getUserByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testCreateUser() {
        User newUser = new User("newuser", "newpassword", "newuser@example.com", ERole.ADMIN);
        User savedUser = userService.createUser(newUser);

        assertNotNull(savedUser);
        assertEquals("newuser", savedUser.getUsername());
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User("updateduser", "updatedpassword", "updateduser@example.com", ERole.ADMIN);
        User result = userService.updateUser(user.getId(), updatedUser);

        assertEquals("updateduser", result.getUsername());
        assertEquals("updateduser@example.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertFalse(foundUser.isPresent());
    }
}
