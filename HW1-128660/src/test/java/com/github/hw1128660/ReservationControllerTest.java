package com.github.hw1128660;

import com.github.hw1128660.controller.ReservationController;
import com.github.hw1128660.entity.Reservation;
import com.github.hw1128660.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void shouldReturnReservationDetails() throws Exception {
        Reservation r = new Reservation();
        r.setToken("ABCD1234");
        r.setRestaurantName("Rest A");
        r.setDate(LocalDate.now());

        when(reservationService.getReservationByToken("ABCD1234")).thenReturn(Optional.of(r));

        mockMvc.perform(get("/api/reservations/ABCD1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value("Rest A"));
    }
}
