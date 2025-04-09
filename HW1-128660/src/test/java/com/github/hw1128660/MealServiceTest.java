package com.github.hw1128660;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.repository.MealRepository;
import com.github.hw1128660.repository.RestaurantRepository;
import com.github.hw1128660.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restA;

    @BeforeEach
    void setup() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();

        restA = restaurantRepository.save(new Restaurant(null, "Rest A", "Aveiro"));
        Restaurant restB = restaurantRepository.save(new Restaurant(null, "Rest B", "Aveiro"));

        mealRepository.save(new Meal(restA, LocalDate.now(), "Chicken and Rice"));
        mealRepository.save(new Meal(restA, LocalDate.now().plusDays(1), "Fish and Potatoes"));
        mealRepository.save(new Meal(restB, LocalDate.now(), "Vegan Wrap"));
    }

    @Test
    void shouldReturnMealsForRestaurant() {
        List<Meal> meals = mealService.getMealsForRestaurant("Rest A");

        assertEquals(2, meals.size());
        assertTrue(meals.stream().allMatch(m -> m.getRestaurant().getName().equals("Rest A")));
    }

    @Test
    void shouldReturnEmptyListForUnknownRestaurant() {
        List<Meal> meals = mealService.getMealsForRestaurant("Rest Z");
        assertTrue(meals.isEmpty());
    }
}
