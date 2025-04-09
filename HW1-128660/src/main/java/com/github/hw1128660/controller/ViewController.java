package com.github.hw1128660.controller;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.MealWithWeather;
import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.entity.WeatherForecast;
import com.github.hw1128660.repository.RestaurantRepository;
import com.github.hw1128660.service.MealService;
import com.github.hw1128660.service.ReservationService;
import com.github.hw1128660.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    @Autowired
    private MealService mealService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "Rest A") String restaurant, Model model) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        List<Meal> meals = mealService.getMealsForRestaurant(restaurant);

        List<MealWithWeather> mealsWithWeather = meals.stream()
                .map(meal -> {
                    WeatherForecast forecast = weatherService.getForecast(
                            meal.getRestaurant().getLocation(), meal.getDate());
                    return new MealWithWeather(meal, forecast);
                })
                .collect(Collectors.toList());

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurants", allRestaurants);
        model.addAttribute("meals", mealsWithWeather);

        return "index";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam String restaurant, @RequestParam String date, Model model) {
        LocalDate parsedDate = LocalDate.parse(date);
        var reservation = reservationService.createReservation(restaurant, parsedDate);
        model.addAttribute("token", reservation.getToken());
        return "confirm";
    }

    @GetMapping("/checkin")
    public String checkinForm() {
        return "checkin";
    }

    @PostMapping("/checkin")
    public String checkin(@RequestParam String token, Model model) {
        boolean result = reservationService.markReservationAsUsed(token);
        model.addAttribute("result", result);
        return "checkin";
    }
}