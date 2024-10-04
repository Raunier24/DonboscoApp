package com.donbosco.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donbosco.models.Flight;

@Repository
public interface IFlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    Optional<Flight> findByAvailableSeats(int availableSeats);
    Boolean findByStatus(boolean status);
    Optional<Flight> findByIdAndDepartureTime(Long id, LocalDateTime departureTime);
    List<Flight> findByDeparture(String departure);
}


    

