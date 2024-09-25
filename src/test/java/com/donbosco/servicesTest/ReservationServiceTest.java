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

        // Crear objetos necesarios para los tests
        user = new User(); // Configura el usuario según sea necesario
        flight = new Flight(); // Configura el vuelo según sea necesario

        reservation1 = new Reservation(LocalDate.now(), 1, user, flight, "John Doe", "Economy", "Confirmed");
        reservation2 = new Reservation(LocalDate.now().plusDays(1), 2, user, flight, "Jane Doe", "Business", "Pending");
    }

    @Test
    public void testFindAllReservations() {
        // Crear una lista de reservas simulada
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        // Simular el comportamiento del servicio
        when(reservationService.findAllReservations()).thenReturn(reservations);

        // Llamar al método y verificar el resultado
        List<Reservation> result = reservationService.findAllReservations();
        assertEquals(2, result.size());
        verify(reservationService, times(1)).findAllReservations();
    }

    @Test
    public void testCreateReservation() {
        // Simular la creación de una reserva
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation1);

        // Llamar al método y verificar el resultado
        Reservation result = reservationService.createReservation(reservation1);
        assertEquals("Confirmed", result.getStatus());
        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }
}
