package com.donbosco.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlightDto {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String departure;

    @NotBlank
    private String destination;

    @NotNull
    @Future
    private LocalDateTime departureTime;

    @NotNull
    @Future
    private LocalDateTime arrivalTime;

    @Positive
    private int availableSeats;

    @NotNull
    private boolean status;

    private List<FlightDto> flight = new ArrayList<FlightDto>();

    public FlightDto(String flightNumber, String departure, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats, boolean status) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.status = status;
    }


    /*private Set<UserDTO> users; pendiente del DTo de USER y si lo voy a IMPLEMENTAR

    public FlightDto(String flightNumber, String departure, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeats, boolean status/*, Set<UserDTO> users) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.status = status;
        //this.users = users; //private Set<UserDTO> users; pendiente del DTo de USER
    }*/

}
