package com.github.hw1128660;

import com.github.hw1128660.controller.WeatherController;
import com.github.hw1128660.entity.WeatherForecast;
import com.github.hw1128660.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void shouldReturnForecast() throws Exception {
        WeatherForecast f = new WeatherForecast(LocalDate.now(), "Cloudy", 18.0);
        when(weatherService.getForecast("Aveiro", LocalDate.now())).thenReturn(f);

        mockMvc.perform(get("/api/weather?location=Aveiro&date=" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value("Cloudy"));
    }
}
