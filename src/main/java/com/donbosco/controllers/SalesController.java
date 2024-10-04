package com.donbosco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donbosco.dto.SalesDto;
import com.donbosco.services.SalesServiceImpl;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;  // Interfaz, no la implementación

    @PostMapping("/reserve")
    public ResponseEntity<String> makeReservation(@RequestBody SalesDto salesDto) {
        try {
            salesService.verifyFlightAvailability(salesDto);
            salesService.processReservation(salesDto);
            return ResponseEntity.ok("Reserva realizada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

