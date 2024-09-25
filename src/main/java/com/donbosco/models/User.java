package com.donbosco.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private ERole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE )//merge y persist recomendadas para manytomany
    @JoinTable(
        name = "user_flight",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private Set<Flight> flights = new HashSet<>();

    public User(String username, String password, String email, ERole role, Set<Reservation> reservations,
            Set<Flight> flights) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.reservations = reservations;
        this.flights = flights;
    }

    // Constructor sin argumentos
    public User() {}

    // Constructor con argumentos
    public User(String username, String password, String email, ERole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ERole getRole() {
        return role;
    }

    public void setRoles(ERole role) {
        this.role = role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }
}
