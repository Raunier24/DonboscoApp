package com.donbosco.controllerTest;

import com.donbosco.controllers.UserController;
import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User.Builder()
                .id(1L)
                .username("juan")
                .email("juan@gmail.com")
                .password("123")
                .role(ERole.USER)
                .build();

        user2 = new User.Builder()
                .id(2L)
                .username("juanita")
                .email("juanita@gmail.com")
                .password("1234")
                .role(ERole.ADMIN)
                .build();

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUser()).thenReturn(userList);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("juan"));
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("juan"));
    }

    @Test
    void updateUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

        User updatedUser = new User.Builder()
                .id(1L)
                .username("juan_updated")
                .email("juan@gmail.com")
                .password("123")
                .role(ERole.USER)
                .build();

        when(userService.createUser(any(User.class))).thenReturn(updatedUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("juan_updated"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}