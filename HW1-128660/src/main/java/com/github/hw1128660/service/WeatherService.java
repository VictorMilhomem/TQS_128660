package com.github.hw1128660.service;

import com.github.hw1128660.entity.OpenWeatherResponse;
import com.github.hw1128660.entity.WeatherForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, CachedWeather> cache = new ConcurrentHashMap<>();
    private final Duration ttl = Duration.ofHours(2);

    private int cacheHits = 0;
    private int cacheMisses = 0;

    public WeatherForecast getForecast(String city, LocalDate date) {
        String key = city + "-" + date;
        CachedWeather cached = cache.get(key);

        if (cached != null && !cached.isExpired(ttl)) {
            cacheHits++;
            return cached.forecast();
        }

        cacheMisses++;
        WeatherForecast fetched = fetchFromOpenWeather(city, date);
        cache.put(key, new CachedWeather(fetched));
        return fetched;
    }

    private WeatherForecast fetchFromOpenWeather(String city, LocalDate targetDate) {
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        OpenWeatherResponse response = restTemplate.getForObject(url, OpenWeatherResponse.class);

        if (response == null || response.getList() == null) {
            return new WeatherForecast(targetDate, "Unknown", 0.0);
        }

        return response.getList().stream()
                .filter(entry -> {
                    LocalDateTime dateTime = LocalDateTime.parse(entry.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    return dateTime.toLocalDate().equals(targetDate);
                })
                .findFirst()
                .map(entry -> new WeatherForecast(
                        targetDate,
                        entry.getWeather().get(0).getDescription(),
                        entry.getMain().getTemp()
                ))
                .orElse(new WeatherForecast(targetDate, "Not available", 0.0));
    }

    public int getCacheHits() {
        return cacheHits;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    private record CachedWeather(WeatherForecast forecast, Instant timestamp) {
        CachedWeather(WeatherForecast f) {
            this(f, Instant.now());
        }

        boolean isExpired(Duration ttl) {
            return timestamp.plus(ttl).isBefore(Instant.now());
        }
    }
}
