package com.donbosco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donbosco.models.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

}
