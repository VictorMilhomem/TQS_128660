package org.example.lab3_2.services;

import org.example.lab3_2.models.Car;
import org.example.lab3_2.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {
    public final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return Optional.ofNullable(carRepository.findByCarId(id));
    }
}
