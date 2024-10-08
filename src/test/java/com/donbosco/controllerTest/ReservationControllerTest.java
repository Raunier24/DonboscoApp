package com.donbosco.controllerTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.donbosco.models.Flight;
import com.donbosco.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.donbosco.controllers.ReservationController;
import com.donbosco.models.Reservation;
import com.donbosco.services.ReservationService;

import jakarta.transaction.Transactional;

@Transactional
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationController = new ReservationController(reservationService);
    }

    @Test
    public void testGetAllReservations() {
        User user = new User();
        Flight flight = new Flight();

        Reservation reservation1 = new Reservation();

        Reservation reservation2 = new Reservation();
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(reservationService.findAllReservations()).thenReturn(reservations);

        List<Reservation> result = reservationController.getAllReservations();

        assertEquals(2, result.size());
        assertEquals(reservation1, result.get(0));
        assertEquals(reservation2, result.get(1));
        verify(reservationService).findAllReservations();
    }

    @Test
    public void testGetReservationById() {
        User user = new User();
        Flight flight = new Flight();

        Reservation reservation = new Reservation();

        when(reservationService.findReservationById(1L)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.getReservationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
        verify(reservationService).findReservationById(1L);
    }

    @Test
    public void testCreateReservation() {
        User user = new User();
        Flight flight = new Flight();

        Reservation newReservation = new Reservation();
        Reservation createdReservation = new Reservation();

        when(reservationService.createReservation(any(Reservation.class))).thenReturn(createdReservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(newReservation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdReservation, response.getBody());
        verify(reservationService).createReservation(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation() {
        ResponseEntity<Void> response = reservationController.deleteReservation(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reservationService).deleteReservation(eq(1L));
    }
}