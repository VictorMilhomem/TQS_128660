package org.example.lab3_2;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.example.lab3_2.models.Car;
import org.example.lab3_2.services.CarManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest
@AutoConfigureMockMvc
public class CarControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    void getAllCars_ReturnsListOfCars() throws Exception {
        Car car1 = new Car(1L, "Toyota", "Corolla");
        Car car2 = new Car(2L, "Honda", "Civic");
        List<Car> cars = Arrays.asList(car1, car2);

        when(carManagerService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].maker").value("Toyota"))
                .andExpect(jsonPath("$[1].maker").value("Honda"));
    }

    @Test
    void getCarById_ReturnsCar() throws Exception {
        Car car = new Car(1L, "Toyota", "Corolla");

        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.maker").value("Toyota"));
    }

    @Test
    void getCarById_NotFound() throws Exception {
        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isNotFound());
    }
}