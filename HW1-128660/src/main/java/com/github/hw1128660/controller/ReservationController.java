package com.github.hw1128660.controller;

import com.github.hw1128660.entity.Reservation;
import com.github.hw1128660.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {


    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> book(@RequestParam String restaurant, @RequestParam String date) {
        LocalDate reservationDate = LocalDate.parse(date);
        Reservation reservation = reservationService.createReservation(restaurant, reservationDate);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Reservation> get(@PathVariable String token) {
        return reservationService.getReservationByToken(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{token}/check-in")
    public ResponseEntity<String> checkIn(@PathVariable String token) {
        boolean success = reservationService.markReservationAsUsed(token);
        return success ? ResponseEntity.ok("Checked in.") : ResponseEntity.badRequest().body("Already used or not found.");
    }
}
