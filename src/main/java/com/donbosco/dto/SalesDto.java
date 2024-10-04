package com.donbosco.dto;

import java.time.LocalDateTime;

public class SalesDto {
    private Long flightId;
    private Long userId;
    private LocalDateTime departureTime; // Para vuelos en diferentes fechas
    private int seats; // Número de asientos solicitados

    // Getters y Setters
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    
}
