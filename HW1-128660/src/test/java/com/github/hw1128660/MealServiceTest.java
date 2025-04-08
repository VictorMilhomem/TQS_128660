package com.github.hw1128660;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.repository.MealRepository;
import com.github.hw1128660.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private MealRepository mealRepository;

    @BeforeEach
    void setup() {
        mealRepository.deleteAll();

        mealRepository.save(new Meal("Rest A", LocalDate.now(), "Chicken + Rice"));
        mealRepository.save(new Meal("Rest A", LocalDate.now().plusDays(1), "Fish + Potatoes"));
        mealRepository.save(new Meal("Rest B", LocalDate.now(), "Vegan Wrap"));
    }

    @Test
    void shouldReturnMealsForRestaurant() {
        List<Meal> meals = mealService.getMealsForRestaurant("Rest A");

        assertEquals(2, meals.size());
        assertTrue(meals.stream().allMatch(m -> m.getRestaurantName().equals("Rest A")));
    }
}