package com.donbosco.servicesTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.donbosco.models.Flight;
import com.donbosco.models.Reservation;
import com.donbosco.models.User;
import com.donbosco.services.ReservationService;

public class ReservationServiceTest {

    @Mock
    private ReservationService reservationService;

    private Reservation reservation1;
    private Reservation reservation2;
    private User user;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(); 
        flight = new Flight(); 

        reservation1 = new Reservation(LocalDate.now(), 1, user, flight, "John Doe", "Economy", "Confirmed");
        reservation2 = new Reservation(LocalDate.now().plusDays(1), 2, user, flight, "Jane Doe", "Business", "Pending");
    }

    @Test
    public void testFindAllReservations() {
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(reservationService.findAllReservations()).thenReturn(reservations);

        List<Reservation> result = reservationService.findAllReservations();
        assertEquals(2, result.size());
        verify(reservationService, times(1)).findAllReservations();
    }

    @Test
    public void testCreateReservation() {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation1);

        Reservation result = reservationService.createReservation(reservation1);
        assertEquals("Confirmed", result.getStatus());
        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }
}
