package com.donbosco.models;

import com.donbosco.repositories.IFlightRepository;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReservationTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IFlightRepository iFlightRepository;

    private User testUser;
    private Flight testFlight;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("Isamar Romero");
        iUserRepository.save(testUser);


        testFlight = new Flight();
        testFlight.setFlightNumber("AB123");
        iFlightRepository.save(testFlight);
    }

    @Test
    public void testCreateReservation() {
        Reservation reservation = new Reservation();
        reservation.setUser(testUser);
        reservation.setFlight(testFlight);
        reservation.setSeats(2);
        reservation.setReservationDate(LocalDateTime.now());


        Reservation savedReservation = reservationRepository.save(reservation);


        assertNotNull(savedReservation.getId());
        assertEquals(testUser.getId(), savedReservation.getUser().getId());
        assertEquals(testFlight.getId(), savedReservation.getFlight().getId());
        assertEquals(2, savedReservation.getSeats());
    }
}