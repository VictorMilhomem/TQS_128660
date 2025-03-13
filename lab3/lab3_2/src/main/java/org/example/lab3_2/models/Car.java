package org.example.lab3_2.models;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String maker;
    private String model;

    public Car(Long carId, String maker, String model) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId.equals(car.carId) && Objects.equals(maker, car.maker) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, maker, model);
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String make) {
        this.maker = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
