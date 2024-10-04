package com.donbosco.controllers;

import com.donbosco.dto.FlightDto;
import com.donbosco.services.IFlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFlightService flightService;

    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
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

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testGetFlightById() throws Exception {
        Mockito.when(flightService.get(flightDto.getId())).thenReturn(flightDto);

        mockMvc.perform(get("/api/flights/{id}", flightDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testCreateFlight() throws Exception {
        Mockito.when(flightService.save(any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"flightNumber\": \"FL123\", \"departure\": \"New York\", \"destination\": \"London\", \"departureTime\": \"" + flightDto.getDepartureTime() + "\", \"arrivalTime\": \"" + flightDto.getArrivalTime() + "\", \"availableSeats\": 100, \"status\": true }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testUpdateFlight() throws Exception {
        Mockito.when(flightService.updateFlight(eq(flightDto.getId()), any(FlightDto.class))).thenReturn(flightDto);

        mockMvc.perform(put("/api/flights/{id}", flightDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"flightNumber\": \"FL123\", \"departure\": \"New York\", \"destination\": \"London\", \"departureTime\": \"" + flightDto.getDepartureTime() + "\", \"arrivalTime\": \"" + flightDto.getArrivalTime() + "\", \"availableSeats\": 100, \"status\": true }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightNumber").value(flightDto.getFlightNumber()))
                .andDo(print());
    }

    @Test
    void testDeleteFlight() throws Exception {
        Mockito.doNothing().when(flightService).delete(flightDto.getId());

        mockMvc.perform(delete("/api/flights/{id}", flightDto.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
