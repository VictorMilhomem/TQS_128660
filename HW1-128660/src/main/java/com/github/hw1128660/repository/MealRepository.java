package com.github.hw1128660.repository;

import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantAndDateGreaterThanEqualOrderByDateAsc(Restaurant restaurant, LocalDate date);

}
