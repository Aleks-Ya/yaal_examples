CREATE DATABASE IF NOT EXISTS people;
USE people;

CREATE TABLE IF NOT EXISTS persons (
  id INT,
  fio STRING,
  city_id INT
);

CREATE TABLE IF NOT EXISTS cities (
  id INT,
  name STRING
);


INSERT INTO TABLE cities VALUES
(1, "Moscow"),
(2, "SPb");

INSERT INTO TABLE persons VALUES
(1, 'Peter', 1),
(2, 'John', 1),
(3, 'Mary', 2);

SELECT * FROM cities;
SELECT * FROM persons;

SELECT * FROM persons p LEFT JOIN cities c ON p.city_id=c.id;
