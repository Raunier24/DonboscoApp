package com.donbosco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donbosco.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
