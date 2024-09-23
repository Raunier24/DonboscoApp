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
import static org.mockito.Mockito.*;

public class ServicesTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void Setup(){
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("isamar");
        user1.setPassword("1234");
        user1.setEmail("isamar@gmail.com");
        user1.setRole(ADMIN);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("sofi");
        user2.setPassword("1234");
        user2.setEmail("sofi@gmail.com");
        user2.setRole(ADMIN);
    }


    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User newUser = userService.createUser(user1);

        assertNotNull(newUser);
        assertNotNull(1L, String.valueOf(newUser.getId()));
        assertNotNull("isamar", newUser.getUsername());

        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void getAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> allUser = userService.getAllUsers();

        assertNotNull(allUser);
        assertEquals(2L, allUser.size());
        assertTrue(allUser.contains(user1));
        assertTrue(allUser.contains(user2));

        verify(userRepository, times(Math.toIntExact(1L))).findAll();
    }

    @Test
    void getUserById() {
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(user1));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user1.getId(), foundUser.get().getId());
        assertEquals(user1.getUsername(), foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(Long.valueOf(1L));
    }

    @Test
    void updateUser() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("UpdatedUsername");

        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        userService.updateUser(userToUpdate, 1L);

        verify(userRepository).save(userToUpdate);

        assertEquals("UpdatedUsername", userToUpdate.getUsername());
        assertEquals(1L, userToUpdate.getId());
    }
    @Test
    void deleteUser() {

        doNothing().when(userRepository).deleteById(Long.valueOf(1L));

        boolean result = userService.deleteUser(1L);

        verify(userRepository).deleteById(Long.valueOf(1L));

        assertTrue(result);

    }
}