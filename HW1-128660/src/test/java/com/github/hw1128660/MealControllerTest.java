package com.github.hw1128660;

import com.github.hw1128660.controller.MealController;
import com.github.hw1128660.entity.Meal;
import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.service.MealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealController.class)
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealService mealService;

    @Test
    void shouldReturnMealsJson() throws Exception {
        Restaurant restA = new Restaurant(1L, "Rest A", "Aveiro");

        List<Meal> meals = List.of(
                new Meal(restA, LocalDate.now(), "Soup + Steak")
        );

        when(mealService.getMealsForRestaurant("Rest A")).thenReturn(meals);

        mockMvc.perform(get("/api/meals?restaurant=Rest A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Soup + Steak"))
                .andExpect(jsonPath("$[0].restaurant.name").value("Rest A"));
    }
}
