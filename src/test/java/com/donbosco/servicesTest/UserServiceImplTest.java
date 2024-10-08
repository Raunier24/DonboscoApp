package com.donbosco.servicesTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.donbosco.dto.UserDto;
import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.services.UserServiceImpl;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        // Limpiamos la base de datos antes de cada prueba
        userRepository.deleteAll();

        // Creamos un usuario de prueba con correos electrónicos únicos
        userDto = new UserDto();
        userDto.setUsername("john_doe");
        userDto.setPassword("password123");
        userDto.setEmail("john_doe1@example.com");  // Cambiaremos este correo en los tests

        user = new User.Builder()
            .username("john_doe")
            .password("password123")
            .email("john_doe1@example.com")  // Cambiaremos este correo en los tests
            .role(ERole.ADMIN)
            .build();

        // Guardar usuario en la base de datos
        userRepository.save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
    }

    @Test
    void testGetUserById() {
        Optional<User> foundUser = userService.getUserById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        Optional<User> foundUser = userService.getUserByUsername("john_doe");
        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());
    }

    @Test
    void testCreateUser() {
        // Cambiar el correo y username para evitar duplicados
        userDto.setUsername("new_user");
        userDto.setEmail("new_user@example.com");

        User savedUser = userService.createUser(userDto);
        assertNotNull(savedUser);
        assertEquals("new_user", savedUser.getUsername());

        // Verificar que ahora haya 2 usuarios en la base de datos
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testUpdateUser() {
        // Primero, actualizamos el UserDto
        userDto.setUsername("updateduser");
        userDto.setEmail("updateduser@example.com");
    
        // Ejecutamos el método de actualización
        User result = userService.updateUser(user.getId(), userDto);
    
        // Verificamos que los cambios se han realizado correctamente
        assertEquals("updateduser", result.getUsername());
        assertEquals("updateduser@example.com", result.getEmail());
    
        // Verificar también que el usuario en la base de datos se ha actualizado
        Optional<User> updatedUser = userRepository.findById(user.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("updateduser", updatedUser.get().getUsername());
        assertEquals("updateduser@example.com", updatedUser.get().getEmail());
    }
    

    @Test
    void testDeleteUser() {
        Optional<User> foundUserBeforeDelete = userRepository.findById(user.getId());
        assertTrue(foundUserBeforeDelete.isPresent());

        userService.deleteUser(user.getId());

        Optional<User> foundUserAfterDelete = userRepository.findById(user.getId());
        assertFalse(foundUserAfterDelete.isPresent());
    }
}
