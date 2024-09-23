package com.donbosco.models;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Object getDetails() {
        throw new UnsupportedOperationException("Unimplemented method 'getDetails'");
    }

    public void setDetails(Object details) {
        throw new UnsupportedOperationException("Unimplemented method 'setDetails'");
    }

    public Object getName() {
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public void setName(Object name) {
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

}
