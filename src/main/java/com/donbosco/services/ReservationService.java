package com.donbosco.services;

import java.util.List;

import com.donbosco.repositories.IReservationRepository;
import org.springframework.stereotype.Service;

import com.donbosco.models.Reservation;
<<<<<<< HEAD
import com.donbosco.repositories.IReservationRepository;
=======
>>>>>>> be0bf596f81e1f264340999cb4512fb9264b1813

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
}
