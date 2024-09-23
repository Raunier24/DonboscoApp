package com.donbosco.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.donbosco.models.Reservation;
import com.donbosco.repositories.IReservationRepository;
import com.donbosco.services.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@DataJpaTest
public class ReservationServiceTest {

    private ReservationService reservationService;
    private IReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        @SuppressWarnings("resource")
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        
        reservationRepository = context.getBean(IReservationRepository.class);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void testFindAllReservations() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> result = reservationService.findAllReservations();

        assertEquals(2, result.size());
    }

    @Test
    public void testCreateReservation() {
        Reservation reservation = new Reservation();

        Reservation createdReservation = reservationService.createReservation(reservation);

        assertNotNull(createdReservation.getId());
        assertEquals("New Reservation", createdReservation.getName());
        assertEquals("New Details", createdReservation.getDetails());
    }

    @Test
    public void testFindReservationById() {
        Reservation reservation = new Reservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        Reservation foundReservation = reservationService.findReservationById(savedReservation.getId());

        assertNotNull(foundReservation);
        assertEquals(savedReservation.getId(), foundReservation.getId());
    }

    @Test
    public void testFindReservationById_NotFound() {
        assertThrows(RuntimeException.class, () -> reservationService.findReservationById(999L),
                "Reservation not found with id: 999");
    }

    @Test
    public void testDeleteReservation() {
        Reservation reservation = new Reservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        reservationService.deleteReservation(savedReservation.getId());

        assertEquals(0, reservationRepository.count());
    }

    @Configuration
    static class TestConfig {

        @Bean
        public IReservationRepository reservationRepository(AnnotationConfigApplicationContext context) {
            return context.getBean(IReservationRepository.class);
        }
    }

}