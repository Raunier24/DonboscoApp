package com.donbosco.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.donbosco.dto.FlightDto;
import com.donbosco.exceptions.BadRequestException;
import com.donbosco.models.Flight;
import com.donbosco.repositories.IFlightRepository;

@SpringBootTest
@Transactional
public class FlightServiceImplTest {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private FlightServiceImpl flightService;

    @Autowired
    private ModelMapper modelMapper;

    private Flight flightEntity;
    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
        flightEntity = new Flight();
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
        // Se convierte el DTO a la entidad
        Flight savedFlightEntity = modelMapper.map(flightDto, Flight.class);
        flightRepository.save(savedFlightEntity);

        // Se verifica que se guardó correctamente
        assertNotNull(savedFlightEntity.getId());
        assertEquals(flightDto.getFlightNumber(), savedFlightEntity.getFlightNumber());
    }

    @Test
    void testGetFlight() {
        // Guardamos el vuelo primero
        flightRepository.save(flightEntity);

        FlightDto retrievedFlight = flightService.get(flightEntity.getId());

        assertNotNull(retrievedFlight);
        assertEquals(flightEntity.getFlightNumber(), retrievedFlight.getFlightNumber());
    }

    @Test
    void testGetAllFlights() {
        flightRepository.save(flightEntity);

        List<FlightDto> retrievedFlights = flightService.getAllFlights();

        assertNotNull(retrievedFlights);
        assertEquals(1, retrievedFlights.size());
    }

    @Test
    void testDeleteFlight() {
        flightRepository.save(flightEntity);

        flightService.delete(flightEntity.getId());

        // Verificamos que el vuelo ya no está en la base de datos
        assertFalse(flightRepository.findById(flightEntity.getId()).isPresent());
    }

    @Test
    void testUpdateFlight() {
        flightRepository.save(flightEntity);

        flightDto.setDestination("Berlin");
        FlightDto updatedFlight = flightService.updateFlight(flightEntity.getId(), flightDto);

        assertNotNull(updatedFlight);
        assertEquals("Berlin", updatedFlight.getDestination());
    }

    @Test
    void testSaveFlightInvalidData() {
        flightDto.setDepartureTime(LocalDateTime.now().plusDays(2));
        flightDto.setArrivalTime(LocalDateTime.now().plusDays(1));

        assertThrows(BadRequestException.class, () -> flightService.save(flightDto));
    }
}
