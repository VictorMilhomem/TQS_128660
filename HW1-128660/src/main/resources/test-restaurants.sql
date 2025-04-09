DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS restaurant;

CREATE TABLE restaurant (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            location VARCHAR(255) NOT NULL
);

INSERT INTO restaurant (name, location) VALUES ('Campus Cafe', 'Building A');
INSERT INTO restaurant (name, location) VALUES ('University Diner', 'Building B');
