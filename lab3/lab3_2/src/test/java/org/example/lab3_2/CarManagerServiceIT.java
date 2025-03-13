package org.example.lab3_2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.example.lab3_2.controllers.CarController;
import org.example.lab3_2.models.Car;
import org.example.lab3_2.repository.CarRepository;
import org.example.lab3_2.services.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CarManagerServiceIT {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCar_Success() {
        Car car = new Car(1L, "Toyota", "Corolla");
        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carManagerService.save(car);
        assertEquals("Toyota", savedCar.getMaker());
        assertEquals("Corolla", savedCar.getModel());
    }

    @Test
    void getAllCars_ReturnsListOfCars() {
        Car car1 = new Car(1L, "Toyota", "Corolla");
        Car car2 = new Car(2L, "Honda", "Civic");
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = carManagerService.getAllCars();
        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getMaker());
        assertEquals("Honda", cars.get(1).getMaker());
    }

    @Test
    void getCarDetails_ReturnsCar() {
        Car car = new Car(1L, "Toyota", "Corolla");
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> result = carManagerService.getCarDetails(1L);
        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().getMaker());
    }

    @Test
    void getCarDetails_NotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Car> result = carManagerService.getCarDetails(1L);
        assertFalse(result.isPresent());
    }
}
