package com.donbosco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donbosco.dto.FlightDto;
import com.donbosco.exceptions.ResourceNotFoundException;
import com.donbosco.services.IFlightService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            return ResponseEntity.noContent().build(); // Código 204: No Content si no hay vuelos
        }
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id) {
        FlightDto flight = flightService.get(id);
        return ResponseEntity.ok(flight);  // Código 200: OK si el vuelo existe
    }

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@Valid @RequestBody FlightDto flightDto) {
        try {
            FlightDto createdFlight = flightService.save(flightDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);  // Código 201: Created si se crea correctamente
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Código 400: Bad Request si hay algún error de validación o en la lógica
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightDto flightDto) {
        try {
            FlightDto updatedFlight = flightService.updateFlight(id, flightDto);
            return ResponseEntity.ok(updatedFlight); // Código 200: OK si se actualiza correctamente
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Flight not found with id: " + id); // Código 404: Not Found si el vuelo no existe
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Código 400: Bad Request si hay un error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        try {
            flightService.delete(id);
            return ResponseEntity.noContent().build();  // Código 204: No Content si se elimina correctamente
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Flight not found with id: " + id);  // Código 404: Not Found si no se encuentra
        } 
    }

    

}
