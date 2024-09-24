package com.donbosco.servicesTest;

import com.donbosco.models.Reservation;
import com.donbosco.repositories.IReservationRepository;
import com.donbosco.services.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private IReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReservations() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.findAllReservations();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void createReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(reservation);

        assertNotNull(createdReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void findReservationById() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.findReservationById(1L);

        assertNotNull(foundReservation);
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void findReservationById_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.findReservationById(1L);
        });

        assertEquals("Reservation not found with id: 1", exception.getMessage());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void deleteReservation() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateReservation() {
        Reservation existingReservation = new Reservation();
        Reservation updatedDetails = new Reservation();
        updatedDetails.setName("Updated Name");
        updatedDetails.setDetails("Updated Details");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(existingReservation);

        Reservation updatedReservation = reservationService.updateReservation(1L, updatedDetails);

        assertEquals("Updated Name", updatedReservation.getName());
        assertEquals("Updated Details", updatedReservation.getDetails());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(existingReservation);
    }
}