package com.donbosco.services;

import java.util.List;

import com.donbosco.dto.FlightDto;

import jakarta.validation.Valid;

public interface IFlightService {

    public FlightDto save(@Valid FlightDto FlightDto);
    public FlightDto get(Long id);
    public List<FlightDto> getAllFlights();
    public FlightDto updateFlight(Long id, @Valid FlightDto flightDto);
    public void delete(Long id);
    
    
}
 