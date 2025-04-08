INSERT INTO meals (restaurant_name, date, description) VALUES
                                                           ('Rest A', CURRENT_DATE, 'Grilled Chicken with Rice'),
                                                           ('Rest A', CURRENT_DATE + INTERVAL 1 DAY, 'Baked Fish with Potatoes'),
                                                           ('Rest A', CURRENT_DATE + INTERVAL 2 DAY, 'Pasta with Tomato Sauce');

-- Sample data for Rest B
INSERT INTO meals (restaurant_name, date, description) VALUES
                                                           ('Rest B', CURRENT_DATE, 'Vegan Burrito Bowl'),
                                                           ('Rest B', CURRENT_DATE + INTERVAL 1 DAY, 'Tofu Stir Fry with Noodles');