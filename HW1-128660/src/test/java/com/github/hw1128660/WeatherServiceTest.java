package com.github.hw1128660;

import com.github.hw1128660.entity.WeatherForecast;
import com.github.hw1128660.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void shouldFetchAndCacheWeather() {
        String location = "Aveiro";
        LocalDate date = LocalDate.now().plusDays(1);

        WeatherForecast forecast1 = weatherService.getForecast(location, date);
        WeatherForecast forecast2 = weatherService.getForecast(location, date);

        assertNotNull(forecast1);
        assertEquals(forecast1, forecast2);
        assertEquals(1, weatherService.getCacheHits());
        assertEquals(1, weatherService.getCacheMisses());
    }
}
