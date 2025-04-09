DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS restaurant;

CREATE TABLE restaurant (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            location VARCHAR(255) NOT NULL
);

CREATE TABLE meal (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      date DATE NOT NULL,
                      description VARCHAR(255) NOT NULL,
                      restaurant_id BIGINT NOT NULL,
                      CONSTRAINT fk_meal_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);


INSERT INTO restaurant (id, name, location) VALUES
                                                (1, 'Rest A', 'Aveiro'),
                                                (2, 'Rest B', 'Aveiro'),
                                                (3, 'Rest C', 'Esgueira');


INSERT INTO meal (date, description, restaurant_id) VALUES
                                                        (CURRENT_DATE, 'Grilled Chicken with Rice', 1),
                                                        (DATEADD('DAY', 1, CURRENT_DATE), 'Baked Fish with Potatoes', 1),
                                                        (DATEADD('DAY', 2, CURRENT_DATE), 'Beef Lasagna with Salad', 1),

                                                        (CURRENT_DATE, 'Vegan Buddha Bowl', 2),
                                                        (DATEADD('DAY', 1, CURRENT_DATE), 'Tofu Stir Fry with Noodles', 2),

                                                        (DATEADD('DAY', 1, CURRENT_DATE), 'Chicken Caesar Wrap', 3),
                                                        (DATEADD('DAY', 2, CURRENT_DATE), 'Salmon with Quinoa', 3);
