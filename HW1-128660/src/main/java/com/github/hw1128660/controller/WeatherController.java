package com.github.hw1128660.controller;

import com.github.hw1128660.entity.WeatherForecast;
import com.github.hw1128660.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {


    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherForecast getForecast(@RequestParam String location, @RequestParam String date) {
        LocalDate forecastDate = LocalDate.parse(date);
        return weatherService.getForecast(location, forecastDate);
    }

    @GetMapping("/cache-stats")
    public Map<String, Integer> getStats() {
        return Map.of(
                "hits", weatherService.getCacheHits(),
                "misses", weatherService.getCacheMisses()
        );
    }
}

