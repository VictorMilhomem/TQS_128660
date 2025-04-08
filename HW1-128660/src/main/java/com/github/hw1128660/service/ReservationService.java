package com.github.hw1128660.service;

import com.github.hw1128660.entity.Reservation;
import com.github.hw1128660.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {


    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(String restaurantName, LocalDate date) {
        String token = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Reservation reservation = new Reservation();
        reservation.setRestaurantName(restaurantName);
        reservation.setDate(date);
        reservation.setToken(token);
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationByToken(String token) {
        return reservationRepository.findByToken(token);
    }

    public boolean markReservationAsUsed(String token) {
        return reservationRepository.findByToken(token).map(res -> {
            if (!res.isUsed()) {
                res.setUsed(true);
                reservationRepository.save(res);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public boolean cancelReservation(String token) {
        return reservationRepository.findByToken(token).map(res -> {
            if (!res.isUsed()) {
                reservationRepository.delete(res);
                return true;
            }
            return false;
        }).orElse(false);
    }
}

