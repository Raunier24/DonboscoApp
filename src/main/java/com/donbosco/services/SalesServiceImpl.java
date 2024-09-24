package com.donbosco.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donbosco.dto.SalesDto;
import com.donbosco.models.Flight;
import com.donbosco.models.Reservation;
import com.donbosco.repositories.IFlightRepository;
import com.donbosco.repositories.ReservationRepository;

@Service
public class SalesServiceImpl implements ISalesService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void verifyFlightAvailability(SalesDto salesDto) {
        // Buscar el vuelo en la base de datos usando el flightId y departureTime
        Flight flight = flightRepository.findByIdAndDepartureTime(salesDto.getFlightId(), salesDto.getDepartureTime())
                .orElseThrow(() -> new RuntimeException("El vuelo no existe o no está disponible"));

        // Verificar si el vuelo está activo (status es true)
        if (!flight.isStatus()) {
            throw new RuntimeException("El vuelo ya no está disponible");
        }

        // Verificar si hay suficientes asientos disponibles
        if (flight.getAvailableSeats() < salesDto.getSeats()) {
            throw new RuntimeException("No hay suficientes asientos disponibles");
        }
    }


    @Override
    public void processReservation(SalesDto salesDto) {
        // Lógica para procesar la reserva (descontar asientos y crear reserva)
        Flight flight = flightRepository.findByIdAndDepartureTime(salesDto.getFlightId(), salesDto.getDepartureTime())
                .orElseThrow(() -> new RuntimeException("El vuelo no existe"));

        // Actualizar el número de asientos
        flight.setAvailableSeats(flight.getAvailableSeats() - salesDto.getSeats());

        // Guardar los cambios en el vuelo
        flightRepository.save(flight);

        // Crear la reserva
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setUserId(salesDto.getUserId());
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(true);

        // Guardar la reserva
        reservationRepository.save(reservation);

        // Verificar si quedan asientos
        if (flight.getAvailableSeats() == 0) {
            flight.setStatus(false);  // Desactivar el vuelo si ya no hay asientos
            flightRepository.save(flight);
        }
    }

    
}
