package com.donbosco.controllerTest;

import com.donbosco.controllers.UserController;
import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user1;
    private User user2;
    private User user3;
    private List<User> userList;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = new User.Builder()
                .id(1)
                .username("juan")
                .email("juan@gmail.com")
                .password("123")
                .role(ERole.USER)
                .build();

        user2 = new User.Builder()
                .id(2)
                .username("juanita")
                .email("juanita@gmail.com")
                .password("1234")
                .role(ERole.ADMIN)
                .build();

        user3 = new User.Builder()
                .id(3)
                .username("paquito")
                .email("paquito@gmail.com")
                .password("12345")
                .role(ERole.ADMIN)
                .build();

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }

    @Test
    void getAllUsersTest() throws Exception{
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    void getUSerByIdTest() throws Exception{
        when(userService.getUserById(1)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void updateUserTest() throws Exception{
        doNothing().when(userService).updateUser(any(User.class), anyInt());

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user2);

        mockMvc.perform(put("/api/user/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserByIdTest() throws Exception{
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/user/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("User with id 1 was deleted"));
    }
}