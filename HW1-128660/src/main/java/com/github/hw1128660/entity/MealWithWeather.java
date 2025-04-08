package com.github.hw1128660.entity;

import java.time.LocalDate;

public class MealWithWeather {
    private String restaurantName;
    private LocalDate date;
    private String description;

    private String weatherSummary;
    private double temperature;

    public MealWithWeather(Meal meal, WeatherForecast weather) {
        this.restaurantName = meal.getRestaurantName();
        this.date = meal.getDate();
        this.description = meal.getDescription();
        this.weatherSummary = weather.getSummary();
        this.temperature = weather.getTemperature();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
