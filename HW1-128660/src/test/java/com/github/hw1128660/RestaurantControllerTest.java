package com.github.hw1128660;

import com.github.hw1128660.controller.RestaurantController;
import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.repository.RestaurantRepository;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    void getAllRestaurants_ReturnsRestaurants() throws Exception {
        // Mock repository response
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant(1L, "Campus Cafe", "Building A"),
                new Restaurant(2L, "University Diner", "Building B")
        );
        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        // Test endpoint
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Campus Cafe"))
                .andExpect(jsonPath("$[1].location").value("Building B"));

        verify(restaurantRepository, times(1)).findAll();
    }
}