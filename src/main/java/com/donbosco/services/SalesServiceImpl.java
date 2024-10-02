package com.donbosco.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donbosco.dto.SalesDto;
import com.donbosco.models.Flight;
import com.donbosco.models.Reservation;
import com.donbosco.models.User;
import com.donbosco.repositories.IFlightRepository;
import com.donbosco.repositories.IUserRepository;
import com.donbosco.repositories.ReservationRepository;

@Service
public class SalesServiceImpl implements ISalesService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void verifyFlightAvailability(SalesDto salesDto) {
        try {

            Flight flight = flightRepository.findById(salesDto.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con ID: " + salesDto.getFlightId()));

            verifyFlightStatus(flight);
            verifySeatAvailability(flight, salesDto.getSeats());

        } catch (RuntimeException e) {
            System.out.println("Error durante la verificación: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void processReservation(SalesDto salesDto) {
        System.out.println("Inicio del proceso de reserva para vuelo con ID: " + salesDto.getFlightId());

        Flight flight = flightRepository.findById(salesDto.getFlightId())
                .orElseThrow(() -> new RuntimeException("El vuelo no existe"));

        User user = userRepository.findById(salesDto.getUserId())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        updateSeatsAndStatus(flight, salesDto.getSeats());
        createReservation(flight, user, salesDto.getSeats());
    }

    private void verifyFlightStatus(Flight flight) {
        if (!flight.isStatus()) {
            System.out.println("El vuelo con ID: " + flight.getId() + " no está disponible.");
            throw new RuntimeException("El vuelo con ID: " + flight.getId() + " no está disponible.");
        } else {
            System.out.println("El vuelo está activo.");
        }
    }

    private void verifySeatAvailability(Flight flight, int requestedSeats) {
        if (flight.getAvailableSeats() < requestedSeats) {
            System.out.println("No hay suficientes asientos. Disponibles: " + flight.getAvailableSeats());
            throw new RuntimeException("No hay suficientes asientos disponibles");
        } else {
            System.out.println("Asientos disponibles: " + flight.getAvailableSeats());
        }
    }

    private void updateSeatsAndStatus(Flight flight, int bookedSeats) {
        flight.setAvailableSeats(flight.getAvailableSeats() - bookedSeats);
        if (flight.getAvailableSeats() == 0) {
            flight.setStatus(false);
        }
        flightRepository.save(flight);
    }

    private void createReservation(Flight flight, User user, int seats) {
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setUser(user);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setSeats(seats);
        reservation.setStatus(true);

        reservationRepository.save(reservation);
        System.out.println("Reserva creada para el vuelo con ID: " + flight.getId());
    }
}

