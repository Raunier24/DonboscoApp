package com.donbosco.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String flightNumber;

    @Column
    private String departure;

    @Column
    private String destination;

    @Column
    private LocalDateTime departureTime;

    @Column
    private LocalDateTime arrivalTime;

    @Column
    private Integer seats;

    @Column
    private Integer availableSeats;

    @Column
    private boolean status;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToMany(mappedBy = "flights")
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    // Constructor privado para uso solo del Builder
    private Flight(String flightNumber, String departure, String destination,
                   LocalDateTime departureTime, LocalDateTime arrivalTime,
                   Integer seats, Integer availableSeats, boolean status,
                   Set<Reservation> reservations, Set<User> users) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
        this.availableSeats = availableSeats;
        this.status = status;
        this.reservations = reservations;
        this.users = users;
    }

    // Constructor sin parámetros para JPA
    public Flight() {}

    // Getters
    public Long getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public Integer getSeats() { return seats; }
    public Integer getAvailableSeats() { return availableSeats; }
    public boolean isStatus() { return status; }
    public Set<Reservation> getReservations() { return reservations; }
    public Set<User> getUsers() { return users; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public void setDeparture(String departure) { this.departure = departure; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
    public void setStatus(boolean status) { this.status = status; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    // Clase Builder
    public static class FlightBuilder {
        private String flightNumber;
        private String departure;
        private String destination;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private Integer seats;
        private Integer availableSeats;
        private boolean status;
        private Set<Reservation> reservations = new HashSet<>();
        private Set<User> users = new HashSet<>();

        public FlightBuilder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public FlightBuilder setDeparture(String departure) {
            this.departure = departure;
            return this;
        }

        public FlightBuilder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public FlightBuilder setDepartureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public FlightBuilder setArrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public FlightBuilder setSeats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public FlightBuilder setAvailableSeats(Integer availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public FlightBuilder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public FlightBuilder setReservations(Set<Reservation> reservations) {
            this.reservations = reservations;
            return this;
        }

        public FlightBuilder setUsers(Set<User> users) {
            this.users = users;
            return this;
        }

        public Flight build() {
            return new Flight(flightNumber, departure, destination,
                    departureTime, arrivalTime, seats,
                    availableSeats, status, reservations, users);
        }
    }
}
