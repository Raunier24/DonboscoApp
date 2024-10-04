package com.donbosco.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.donbosco.models.Reservation;
import com.donbosco.repositories.IReservationRepository;

@Service
public class ReservationService {

    private final IReservationRepository reservationRepository;

    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation existingReservation = findReservationById(id);
        existingReservation.setReservationDate(LocalDateTime.now());
        existingReservation.setSeats(existingReservation.getSeats());
        return reservationRepository.save(existingReservation);
    }

}
