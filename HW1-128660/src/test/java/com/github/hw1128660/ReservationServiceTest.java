package com.github.hw1128660;

import com.github.hw1128660.entity.Reservation;
import com.github.hw1128660.repository.ReservationRepository;
import com.github.hw1128660.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setup() {
        reservationRepository.deleteAll();
    }

    @Test
    void shouldCreateReservationWithUniqueToken() {
        Reservation reservation = reservationService.createReservation("Rest A", LocalDate.now());

        assertNotNull(reservation.getId());
        assertNotNull(reservation.getToken());
        assertFalse(reservation.isUsed());
    }
}
