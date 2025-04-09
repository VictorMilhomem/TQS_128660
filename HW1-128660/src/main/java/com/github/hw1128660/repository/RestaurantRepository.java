package com.github.hw1128660.repository;

import com.github.hw1128660.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);
}
