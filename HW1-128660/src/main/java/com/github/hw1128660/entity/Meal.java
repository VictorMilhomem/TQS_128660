package com.github.hw1128660.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Meal {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private LocalDate date;
    private String description;

    public Meal(Long id, Restaurant restaurant, LocalDate date, String description) {
        this.id = id;
        this.restaurant = restaurant;
        this.date = date;
        this.description = description;
    }


    public Meal(Restaurant restaurant, LocalDate date, String description) {
        this.restaurant = restaurant;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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


