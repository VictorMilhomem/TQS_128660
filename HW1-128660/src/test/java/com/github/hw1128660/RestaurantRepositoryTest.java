package com.github.hw1128660;


import com.github.hw1128660.entity.Restaurant;
import com.github.hw1128660.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Sql("/test-restaurants.sql")
    void findAll_ReturnsAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(2);

    }
}