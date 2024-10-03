package com.donbosco.servicesTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.donbosco.dto.UserDto;
import com.donbosco.models.ERole;
import static com.donbosco.models.ERole.ADMIN;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.services.UserService;

public class UserServicesTest {
    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private UserDto userDto1; // Declarar userDto1
    private UserDto userDto2; // Declarar userDto2

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Inicializar UserDto para user1
        userDto1 = new UserDto();
        userDto1.setUsername("isamar");
        userDto1.setPassword("1234");
        userDto1.setEmail("isamar@gmail.com");

        // Inicializar UserDto para user2
        userDto2 = new UserDto();
        userDto2.setUsername("AnaMary");
        userDto2.setPassword("1234");
        userDto2.setEmail("anamary@gmail.com");

        // Crear User entities usando el Builder
        user1 = new User.Builder()
            .id(1L)
            .username("isamar")
            .password("1234")
            .email("isamar@gmail.com")
            .role(ERole.ADMIN)
            .build();

        user2 = new User.Builder()
            .id(2L)
            .username("AnaMary")
            .password("1234")
            .email("anamary@gmail.com")
            .role(ERole.ADMIN)
            .build();
    }

    @Test
    void createUser() {
        when(iUserRepository.save(any(User.class))).thenReturn(user1);

        // Convertir UserDto a User
        UserDto userDto = userDto1;
        User newUser = userService.createUser(userDto);

        assertNotNull(newUser);
        assertEquals(1L, newUser.getId());
        assertEquals("isamar", newUser.getUsername());

        verify(iUserRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(iUserRepository.findAll()).thenReturn(userList);

        List<User> allUser = userService.getAllUsers();

        assertNotNull(allUser);
        assertEquals(2, allUser.size());
        assertTrue(allUser.contains(user1));
        assertTrue(allUser.contains(user2));

        verify(iUserRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user1.getId(), foundUser.get().getId());
        assertEquals(user1.getUsername(), foundUser.get().getUsername());
        verify(iUserRepository, times(1)).findById(1L);
    }

    @Test
    void updateUser() {
        User updatedUser = new User.Builder()
            .id(1L)
            .username("UpdatedUsername")
            .password("1234")
            .email("updated@gmail.com")
            .role(ADMIN)
            .build();

        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(iUserRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.updateUser(1L, updatedUser);

        verify(iUserRepository).save(updatedUser);

        assertEquals("UpdatedUsername", updatedUser.getUsername());
        assertEquals(1L, updatedUser.getId());
    }

    @Test
    void deleteUser() {
        doNothing().when(iUserRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(iUserRepository).deleteById(1L);
    }
}
