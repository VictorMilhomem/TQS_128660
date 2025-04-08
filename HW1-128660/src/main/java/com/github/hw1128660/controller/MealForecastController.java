package com.github.hw1128660.controller;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.MealWithWeather;
import com.github.hw1128660.entity.WeatherForecast;
import com.github.hw1128660.service.MealService;
import com.github.hw1128660.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meal-forecast")
public class MealForecastController {


    private MealService mealService;
    private WeatherService weatherService;

    public MealForecastController(MealService mealService, WeatherService weatherService) {
        this.mealService = mealService;
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<MealWithWeather> getMealsWithWeather(@RequestParam String restaurant) {
        List<Meal> meals = mealService.getMealsForRestaurant(restaurant);

        return meals.stream()
                .map(meal -> {
                    WeatherForecast weather = weatherService.getForecast("Aveiro", meal.getDate()); // or pass based on location
                    return new MealWithWeather(meal, weather);
                })
                .toList();
    }
}
