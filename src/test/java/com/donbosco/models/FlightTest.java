package com.donbosco.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {
    @Test
    void testFlightBuilder() {
        // Valores de prueba
        String flightNumber = "AB123";
        String departure = "New York";
        String destination = "London";
        LocalDateTime departureTime = LocalDateTime.of(2024, 10, 10, 14, 0);
        LocalDateTime arrivalTime = LocalDateTime.of(2024, 10, 10, 20, 0);
        Integer seats = 200;
        Integer availableSeats = 150;
        boolean status = true;
        HashSet<Reservation> reservations = new HashSet<>();
        HashSet<User> users = new HashSet<>();

        // Construcción del objeto Flight
        Flight flight = new Flight.FlightBuilder()
                .setFlightNumber(flightNumber)
                .setDeparture(departure)
                .setDestination(destination)
                .setDepartureTime(departureTime)
                .setArrivalTime(arrivalTime)
                .setSeats(seats)
                .setAvailableSeats(availableSeats)
                .setStatus(status)
                .setReservations(reservations)
                .setUsers(users)
                .build();

        // Validación de valores
        assertEquals(flightNumber, flight.getFlightNumber());
        assertEquals(departure, flight.getDeparture());
        assertEquals(destination, flight.getDestination());
        assertEquals(departureTime, flight.getDepartureTime());
        assertEquals(arrivalTime, flight.getArrivalTime());
        assertEquals(seats, flight.getSeats());
        assertEquals(availableSeats, flight.getAvailableSeats());
        assertEquals(status, flight.isStatus());
        assertEquals(reservations, flight.getReservations());
        assertEquals(users, flight.getUsers());
    }

    @Test
    void testDefaultValues() {
        // Verificación de valores predeterminados (si fuera necesario)
        Flight flight = new Flight();
        assertNull(flight.getId());
        assertNull(flight.getFlightNumber());
        assertNull(flight.getDeparture());
        assertNull(flight.getDestination());
        assertNull(flight.getDepartureTime());
        assertNull(flight.getArrivalTime());
        assertNull(flight.getSeats());
        assertNull(flight.getAvailableSeats());
        assertFalse(flight.isStatus());
        assertTrue(flight.getReservations().isEmpty());
        assertTrue(flight.getUsers().isEmpty());
    }
}
