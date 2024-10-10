package com.donbosco.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    private User user;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("Test User");
        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("FL123");
    }

    @Test
    public void testReservationBuilder() {
        LocalDateTime reservationDate = LocalDateTime.now();

        Reservation reservation = new Reservation.Builder()
                .reservationDate(reservationDate)
                .status(true)
                .seats(2)
                .user(user)
                .flight(flight)
                .build();

        assertNotNull(reservation);
        assertEquals(reservationDate, reservation.getReservationDate());
        assertTrue(reservation.isStatus());
        assertEquals(2, reservation.getSeats());
        assertEquals(user, reservation.getUser());
        assertEquals(flight, reservation.getFlight());
    }

    @Test
    public void testReservationStatus() {
        Reservation reservation = new Reservation.Builder()
                .status(false)
                .build();

        assertFalse(reservation.isStatus());

        reservation.setStatus(true);
        assertTrue(reservation.isStatus());
    }

    @Test
    public void testReservationEquality() {
        Reservation reservation1 = new Reservation.Builder().user(user).flight(flight).build();
        Reservation reservation2 = new Reservation.Builder().user(user).flight(flight).build();

        reservation1.setId(1L);
        reservation2.setId(1L);

        assertEquals(reservation1, reservation2);
    }

    @Test
    public void testReservationSeats() {
        Reservation reservation = new Reservation.Builder().seats(3).build();
        assertEquals(3, reservation.getSeats());

        reservation.setSeats(5);
        assertEquals(5, reservation.getSeats());
    }
}
