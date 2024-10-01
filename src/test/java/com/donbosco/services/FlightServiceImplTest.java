package com.donbosco.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.donbosco.dto.FlightDto;
import com.donbosco.exceptions.BadRequestException;
import com.donbosco.exceptions.ResourceNotFoundException;
import com.donbosco.models.Flight;
import com.donbosco.repositories.IFlightRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight flightEntity;
    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
        flightEntity = new Flight();
        flightEntity.setId(1L);
        flightEntity.setFlightNumber("AB123");
        flightEntity.setDeparture("Madrid");
        flightEntity.setDestination("Paris");
        flightEntity.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightEntity.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flightEntity.setAvailableSeats(150);
        flightEntity.setStatus(true);

        // Usar el constructor sin ID en el DTO
        flightDto = new FlightDto(
                "AB123",
                "Madrid",
                "Paris",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                150,
                true
        );
    }

    @Test
    void testSaveFlight() {
        when(modelMapper.map(any(FlightDto.class), eq(Flight.class))).thenReturn(flightEntity);
        when(flightRepository.save(any(Flight.class))).thenReturn(flightEntity);
        when(modelMapper.map(any(Flight.class), eq(FlightDto.class))).thenReturn(flightDto);

        FlightDto savedFlight = flightService.save(flightDto);

        assertNotNull(savedFlight);
        assertEquals(flightDto.getFlightNumber(), savedFlight.getFlightNumber());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testGetFlight() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));
        when(modelMapper.map(any(Flight.class), eq(FlightDto.class))).thenReturn(flightDto);

        FlightDto retrievedFlight = flightService.get(1L);

        assertNotNull(retrievedFlight);
        assertEquals(flightDto.getFlightNumber(), retrievedFlight.getFlightNumber());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllFlights() {
        List<Flight> flights = Arrays.asList(flightEntity);
        when(flightRepository.findAll()).thenReturn(flights);
        when(modelMapper.map(any(Flight.class), eq(FlightDto.class))).thenReturn(flightDto);

        List<FlightDto> retrievedFlights = flightService.getAllFlights();

        assertNotNull(retrievedFlights);
        assertEquals(1, retrievedFlights.size());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testDeleteFlight() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));

        flightService.delete(1L);

        verify(flightRepository, times(1)).delete(flightEntity);
    }

    @Test
    void testUpdateFlight() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));
        when(flightRepository.save(any(Flight.class))).thenReturn(flightEntity);
        when(modelMapper.map(any(Flight.class), eq(FlightDto.class))).thenReturn(flightDto);

        FlightDto updatedFlight = flightService.updateFlight(1L, flightDto);

        assertNotNull(updatedFlight);
        assertEquals(flightDto.getFlightNumber(), updatedFlight.getFlightNumber());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testSaveFlightInvalidData() {
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(2));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1));

        assertThrows(BadRequestException.class, () -> flightService.save(flightDto));
    }
}
