package com.donbosco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donbosco.models.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

}
