package org.example.lab3_2.repository;

import org.example.lab3_2.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    public Car findByCarId(Long id);
    public List<Car> findAll();
}
