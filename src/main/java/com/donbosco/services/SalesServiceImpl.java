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
        System.out.println("Inicio de verificación de vuelo con ID: " + salesDto.getFlightId());

        // Buscar el vuelo solo por el flightId
        Flight flight = flightRepository.findById(salesDto.getFlightId())
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con ID: " + salesDto.getFlightId()));

        System.out.println("Vuelo encontrado: " + flight.getFlightNumber());

        // Verificar si el vuelo está activo
        if (!flight.isStatus()) {
            System.out.println("El vuelo con ID: " + salesDto.getFlightId() + " no está disponible.");
            throw new RuntimeException("El vuelo ya no está disponible");
        } else {
            System.out.println("El vuelo está activo.");
        }

        // Verificar si hay suficientes asientos disponibles
        if (flight.getAvailableSeats() < salesDto.getSeats()) {
            System.out.println("No hay suficientes asientos. Disponibles: " + flight.getAvailableSeats());
            throw new RuntimeException("No hay suficientes asientos disponibles");
        } else {
            System.out.println("Asientos disponibles: " + flight.getAvailableSeats());
        }

    } catch (RuntimeException e) {
        System.out.println("Error durante la verificación: " + e.getMessage());
        throw e;  // Rethrow la excepción para que se gestione más arriba si es necesario
    }
}



    @Override
    public void processReservation(SalesDto salesDto) {
        System.out.println("Inicio del proceso de reserva para vuelo con ID: " + salesDto.getFlightId());
        // Lógica para procesar la reserva (descontar asientos y crear reserva)
        Flight flight = flightRepository.findByIdAndDepartureTime(salesDto.getFlightId(), salesDto.getDepartureTime())
                .orElseThrow(() -> new RuntimeException("El vuelo no existe"));
        
        System.out.println("User con ID: " + salesDto.getUserId());
        User user = userRepository.findById(salesDto.getUserId())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        // Actualizar el número de asientos
        flight.setAvailableSeats(flight.getAvailableSeats() - salesDto.getSeats());

         // Verificar si quedan asientos
         if (flight.getAvailableSeats() == 0) {
            flight.setStatus(false);  
            flightRepository.save(flight);
        }

        // Guardar los cambios en el vuelo
        flightRepository.save(flight);

        // Crear la reserva
        System.out.println("User ID en SalesDto: " + salesDto.getUserId());

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setUser(user);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setSeats(salesDto.getSeats());
        reservation.setStatus(true);

        // Guardar la reserva
        reservationRepository.save(reservation);

       
    }

    
}
