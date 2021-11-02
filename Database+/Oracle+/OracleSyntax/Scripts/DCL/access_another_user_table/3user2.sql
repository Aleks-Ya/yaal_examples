-- As "user2"

-- Read another user table
SELECT * FROM user1.cities;

-- Write another user table
INSERT INTO user1.cities(city, established) VALUES ('Sochi', 1838);

DROP TABLE user1.cities;