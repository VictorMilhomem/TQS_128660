package com.github.hw1128660.service;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.repository.MealRepository;
import com.github.hw1128660.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {


    private MealRepository mealRepository;
    private RestaurantRepository restaurantRepository;

    public MealService(MealRepository mealRepository, RestaurantRepository restaurantRepository) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Meal> getMealsForRestaurant(String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByName(restaurantName);
        if (restaurant == null) return List.of();
        return mealRepository.findByRestaurantAndDateGreaterThanEqualOrderByDateAsc(
                restaurant, LocalDate.now());
    }
}