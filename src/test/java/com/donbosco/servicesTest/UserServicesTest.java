package com.donbosco.servicesTest;

import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.donbosco.models.ERole.ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServicesTest {
    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("isamar");
        user1.setPassword("1234");
        user1.setEmail("isamar@gmail.com");
        user1.setRole(ADMIN);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("AnaMary");
        user2.setPassword("1234");
        user2.setEmail("anamary@gmail.com");
        user2.setRole(ADMIN);
    }

    @Test
    void createUser() {
        when(iUserRepository.save(any(User.class))).thenReturn(user1);

        User newUser = userService.createUser(user1);

        assertNotNull(newUser);
        assertEquals(1L, newUser.getId());
        assertEquals("isamar", newUser.getUsername());

        verify(iUserRepository, times(1)).save(user1);
    }

    @Test
    void getAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(iUserRepository.findAll()).thenReturn(userList);

        List<User> allUser = userService.getAllUser();

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
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("UpdatedUsername");

        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(iUserRepository.save(any(User.class))).thenReturn(userToUpdate);

        userService.updateUser(userToUpdate, 1L);

        verify(iUserRepository).save(userToUpdate);

        assertEquals("UpdatedUsername", userToUpdate.getUsername());
        assertEquals(1L, userToUpdate.getId());
    }

    @Test
    void deleteUser() {
        doNothing().when(iUserRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        verify(iUserRepository).deleteById(1L);

        assertTrue(result);
    }
}