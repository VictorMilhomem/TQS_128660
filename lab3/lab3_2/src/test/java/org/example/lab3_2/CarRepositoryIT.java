package org.example.lab3_2;

import static org.junit.jupiter.api.Assertions.*;

import org.example.lab3_2.models.Car;
import org.example.lab3_2.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.h2.console.enabled=true",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class CarRepositoryIT {

    @Autowired
    private CarRepository carRepository;

    @Test
    @Rollback(false)
    void saveCar_Success() {
        Car car = new Car(null, "Ford", "Focus");
        Car savedCar = carRepository.save(car);

        assertNotNull(savedCar.getCarId());
        assertEquals("Ford", savedCar.getMaker());
        assertEquals("Focus", savedCar.getModel());
    }

    @Test
    void findAllCars_ReturnsListOfCars() {
        Car car = new Car(null, "Chevrolet", "Camaro");
        car = carRepository.save(car);
        List<Car> cars = carRepository.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    void findCarById_ReturnsCar() {
        Car car = new Car(null, "Chevrolet", "Camaro");
        car = carRepository.save(car);

        Optional<Car> foundCar = carRepository.findById(car.getCarId());
        assertTrue(foundCar.isPresent());
        assertEquals("Chevrolet", foundCar.get().getMaker());
    }

    @Test
    void deleteCar_RemovesCar() {
        Car car = new Car(null, "Mazda", "CX-5");
        car = carRepository.save(car);
        Long carId = car.getCarId();

        carRepository.deleteById(carId);
        Optional<Car> foundCar = carRepository.findById(carId);
        assertFalse(foundCar.isPresent());
    }
}
