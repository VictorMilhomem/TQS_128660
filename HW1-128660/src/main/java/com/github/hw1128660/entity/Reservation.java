package com.github.hw1128660.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String restaurantName;

    private LocalDate date;

    private String token;

    private boolean used = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Reservation(Long id, String restaurantName, LocalDate date, String token, boolean used, LocalDateTime createdAt) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.date = date;
        this.token = token;
        this.used = used;
        this.createdAt = createdAt;
    }

    public Reservation(String restaurantName, LocalDate date, String token, boolean used) {
        this.restaurantName = restaurantName;
        this.date = date;
        this.token = token;
        this.used = used;
    }

    public Reservation() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
