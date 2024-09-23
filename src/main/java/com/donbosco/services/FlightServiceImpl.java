package com.donbosco.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donbosco.dto.FlightDto;
import com.donbosco.exceptions.BadRequestException;
import com.donbosco.exceptions.ResourceNotFoundException;
import com.donbosco.models.Flight;
import com.donbosco.repositories.IFlightRepository;

import jakarta.validation.Valid;

@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FlightDto save(@Valid FlightDto flightDto) {
        // Validar si los datos del vuelo son inválidos (ejemplo de validación)
        if (flightDto.getDeparture() == null || flightDto.getDestination() == null) {
            throw new BadRequestException("Departure and arrival dates must be provided");
        }
        
        if (flightDto.getDepartureTime().isAfter(flightDto.getArrivalTime())) {
            throw new BadRequestException("Departure date cannot be after arrival date");
        }

        // Convertir DTO a entidad
        Flight flight = modelMapper.map(flightDto, Flight.class);
        
        // Guardar en la base de datos
        Flight savedFlight = flightRepository.save(flight);
        
        // Convertir entidad a DTO
        return modelMapper.map(savedFlight, FlightDto.class);
    }

    @Override
    public FlightDto get(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return modelMapper.map(flight, FlightDto.class);
    }

    @Override
    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                      .map(flight -> modelMapper.map(flight, FlightDto.class))
                      .toList();
    }

    @Override
    public void delete(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }
}
