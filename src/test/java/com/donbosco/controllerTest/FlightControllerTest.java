package com.donbosco.controllerTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.donbosco.dto.FlightDto;
import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.models.ERole;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.services.IFlightService;
import com.donbosco.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.donbosco.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFlightService flightService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private FlightDto flightDto;
    private String token; // Token JWT generado
    private String token; // Token JWT generado

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        userRepository.deleteAll();

        // Crear un usuario administrador de prueba
        User user = new User.Builder()
                .username("admin_user")
                .password("password123")
                .email("admin@example.com")
                .role(ERole.ADMIN)
                .build();
        
        user = userRepository.save(user);

        // Generar un token JWT para el usuario creado
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_ADMIN")
                .build();
        
        token = jwtService.getTokenService(userDetails); // Generar el token

        // Crear un FlightDto para usar en los tests
        flightDto = new FlightDto(
                1L,
                "FL123",
                "New York",
                "London",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                100,
                true,
                Collections.emptyList()
        );
    }

    @Test
    void testGetAllFlights() throws Exception {
        Mockito.when(flightService.getAllFlights()).thenReturn(List.of(flightDto));

        mockMvc.perform(get("/api/flights")
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
        mockMvc.perform(get("/api/flights")
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testGetFlightById() throws Exception {
        Mockito.when(flightService.get(flightDto.getId())).thenReturn(flightDto);

        mockMvc.perform(get("/api/flights/{id}", flightDto.getId())
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
        mockMvc.perform(get("/api/flights/{id}", flightDto.getId())
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testCreateFlight() throws Exception {
        Mockito.when(flightService.save(any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(post("/api/flights")
                        .header("Authorization", "Bearer " + token) // Incluir el token JWT
                        .header("Authorization", "Bearer " + token) // Incluir el token JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightDto)))
                        .content(objectMapper.writeValueAsString(flightDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testUpdateFlight() throws Exception {
        Mockito.when(flightService.updateFlight(eq(flightDto.getId()), any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(put("/api/flights/{id}", flightDto.getId())
                        .header("Authorization", "Bearer " + token) // Incluir el token JWT
                        .header("Authorization", "Bearer " + token) // Incluir el token JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightDto)))
                        .content(objectMapper.writeValueAsString(flightDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testDeleteFlight() throws Exception {
        Mockito.doNothing().when(flightService).delete(flightDto.getId());

        mockMvc.perform(delete("/api/flights/{id}", flightDto.getId())
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
        mockMvc.perform(delete("/api/flights/{id}", flightDto.getId())
                        .header("Authorization", "Bearer " + token)) // Incluir el token JWT
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}