package com.github.hw1128660.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Meal {
    @Id
    @GeneratedValue
    private Long id;

    private String restaurantName;
    private LocalDate date;
    private String description;

    public Meal(Long id, String restaurantName, LocalDate date, String description) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.date = date;
        this.description = description;
    }

    public Meal(String restaurantName, LocalDate date, String description) {
        this.restaurantName = restaurantName;
        this.date = date;
        this.description = description;
    }

    public Meal() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}


