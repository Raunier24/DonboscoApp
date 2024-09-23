package com.donbosco.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donbosco.models.Flight;

@Repository
public interface  IFlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    Optional<Flight> findByAvailableSeats(int availableSeats);
    Boolean findByStatus(boolean status); 
}
    

