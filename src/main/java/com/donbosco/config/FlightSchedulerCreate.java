package com.donbosco.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.donbosco.models.Flight;
import com.donbosco.repositories.IFlightRepository;

@Service
public class FlightSchedulerCreate {

    @Autowired
    private IFlightRepository flightRepository;

    @Scheduled(cron = "45 21 * * 4 ?")  // Se ejecuta todos los días a las 00:30 "0 30 0 * * ?"   1
    public void generateNextDayFlights() {
        System.out.println("Iniciando la creación de vuelos para el día siguiente...");

        // Obtiene todos los vuelos actuales
        List<Flight> flights = flightRepository.findAll();

        for (Flight flight : flights) {
            // Solo vuelos activos
            if (flight.isStatus()) {
                // Clona el vuelo
                Flight newFlight = new Flight();
                newFlight.setFlightNumber(flight.getFlightNumber());
                newFlight.setDeparture(flight.getDeparture());
                newFlight.setDestination(flight.getDestination());
                newFlight.setAvailableSeats(flight.getSeats());
                newFlight.setStatus(true);  // Los nuevos vuelos están activos

                // Ajusta las fechas respetando la hora original
                newFlight.setDepartureTime(flight.getDepartureTime().plusDays(1));
                newFlight.setArrivalTime(flight.getArrivalTime().plusDays(1));

                // Guarda el nuevo vuelo
                flightRepository.save(newFlight);
            }
        }

        System.out.println("Vuelos para el día siguiente creados con éxito.");
    }
}

