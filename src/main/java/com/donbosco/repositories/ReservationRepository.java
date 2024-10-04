package com.donbosco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donbosco.models.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
