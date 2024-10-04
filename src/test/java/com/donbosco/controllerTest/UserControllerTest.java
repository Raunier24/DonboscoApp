package com.donbosco.controllerTest;

import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import com.donbosco.dto.UserDto;
import com.donbosco.services.UserService;
import com.donbosco.services.JwtService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService; // Inyectar el JwtService

    private User user;
    private UserDto userDto;
    private String token; // Para almacenar el token JWT
    

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Limpiamos la base de datos antes de cada test
    
        // Creamos un usuario de prueba
        userDto = new UserDto();
        userDto.setUsername("john_doe");
        userDto.setPassword("password123");
        userDto.setEmail("john@example.com");
    
        // Crear User entities usando el Builder
        user = new User.Builder()
            .id(1L)
            .username("john_doe") // Asegúrate de que el nombre coincide
            .password("password123")
            .email("john@example.com")
            .role(ERole.ADMIN)
            .build();
    
        // Guardar el usuario en la base de datos
        user = userRepository.save(user);
    
        // Generar un token JWT para el usuario que acabamos de crear
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername()) // Asegúrate de que usas el mismo nombre de usuario
            .password(user.getPassword())
            .authorities("ROLE_ADMIN")
            .build();
        token = jwtService.getTokenService(userDetails); // Generar el token
    }
    

    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + token)) // Usar el token generado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())));
    }

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/{id}", user.getId())
                .header("Authorization", "Bearer " + token)) // Usar el token generado
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    void shouldReturnNotFoundForInvalidUserId() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 999L)
                .header("Authorization", "Bearer " + token)) // Usar el token generado
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        // Crear un UserDto con un username único
        UserDto uniqueUserDto = new UserDto();
        uniqueUserDto.setUsername("unique_username");
        uniqueUserDto.setPassword("password123");
        uniqueUserDto.setEmail("unique@example.com");
        uniqueUserDto.setRole(ERole.ADMIN);

        // Crear el nuevo usuario
        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + token) // Usar el token generado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(uniqueUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(uniqueUserDto.getUsername())));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        // Crear o reutilizar un usuario que ya exista en la base de datos para esta prueba
        User user = new User.Builder()
                .username("unique_username")
                .password("password123")
                .email("unique@example.com")
                .role(ERole.ADMIN)
                .build();

        user = userRepository.save(user);  // Guardar el usuario en la base de datos

        // Actualizar el nombre de usuario del usuario
        user.setUsername("frodo");

        // Generar un nuevo token JWT para este usuario
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities("ROLE_ADMIN")
            .build();
        token = jwtService.getTokenService(userDetails); // Generar el token JWT para el usuario actualizado

        // Ejecutar la prueba de actualización
        mockMvc.perform(put("/api/users/{id}", user.getId())
                        .header("Authorization", "Bearer " + token) // Usar el token generado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())));
}


    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", user.getId())
                        .header("Authorization", "Bearer " + token)) // Usar el token generado
                .andExpect(status().isNoContent());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assert (deletedUser.isEmpty());
    }
}
