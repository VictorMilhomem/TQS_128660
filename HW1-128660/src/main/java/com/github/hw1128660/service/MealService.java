package com.github.hw1128660.service;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {


    private MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getMealsForRestaurant(String restaurant) {
        return mealRepository.findByRestaurantNameOrderByDateAsc(restaurant);
    }
}