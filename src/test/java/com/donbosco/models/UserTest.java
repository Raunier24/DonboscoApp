package com.donbosco.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Test
    void testConstructorSinArgumentos() {
        // Given
        User user = new User();

        // Then
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getRole());
        assertNull(user.getReservations());
    }

    @Test
    void testConstructorConArgumentos() {
        // Given
        String username = "username";
        String password = "password";
        String email = "email";
        ERole role = ERole.ADMIN;
        Set<Reservation> reservations = new HashSet<>();
        Set<Flight> flights = new HashSet<>();

        User user = new User(username, password, email, role, reservations, flights);

        // Then
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(reservations, user.getReservations());
        assertEquals(flights, user.getFlights());
    }

    @Test
    void testGettersYSetters() {
        // Given
        User user = new User();

        // When
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(ERole.ADMIN);
        Set<Reservation> reservations = new HashSet<>();
        user.setReservations(reservations);
        Set<Flight> flights = new HashSet<>();
        user.setFlights(flights);

        // Then
        assertEquals(1L, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("email", user.getEmail());
        assertEquals(ERole.ADMIN, user.getRole());
        assertEquals(reservations, user.getReservations());
        assertEquals(flights, user.getFlights());
    }
}