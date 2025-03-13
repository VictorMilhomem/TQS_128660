package org.example.lab3_2.controllers;

import org.example.lab3_2.models.Car;
import org.example.lab3_2.services.CarManagerService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(car);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/cars", produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId)  throws ResourceNotFoundException {
        Car car = carManagerService.getCarDetails(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for id: " + carId));

        return ResponseEntity.ok().body(car);
    }
}
