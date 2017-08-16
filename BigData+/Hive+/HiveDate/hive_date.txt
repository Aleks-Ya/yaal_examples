--Hive 1.2.2: working with dates

CREATE DATABASE iablokov_test;
USE iablokov_test;

CREATE TABLE persons(
    name STRING,
    birthday DATE
);

--not work
INSERT INTO persons VALUES ("John", to_date("1970-01-01")), ("Mary", to_date("1980-01-01"));
INSERT INTO persons VALUES ("John", CAST(to_date("1970-01-01") AS DATE));
INSERT INTO TABLE persons VALUES ("John", current_date());
INSERT INTO TABLE persons SELECT "John", current_date();
INSERT INTO TABLE persons SELECT "John", NULL;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John", NULL);

--works
INSERT INTO TABLE persons VALUES ("John", NULL);
SELECT sub.name, sub.birthday FROM (SELECT "John" as name, NULL as birthday) sub;
INSERT INTO TABLE persons SELECT sub.name, sub.birthday FROM (SELECT "John" as name, NULL as birthday) sub;
INSERT INTO TABLE persons SELECT sub.name, sub.birthday FROM (SELECT "John" as name, current_date() as birthday) sub;
INSERT INTO TABLE persons SELECT sub.name, sub.birthday FROM (SELECT "John" as name, to_date("1970-01-01") as birthday) sub;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John" as name, to_date("1970-01-01") as birthday) sub;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John", to_date("1970-01-01")) sub;


--Select rows that contain the latest date
TRUNCATE TABLE persons;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John", to_date("1970-01-01")) sub;
INSERT INTO TABLE persons SELECT * FROM (SELECT "Mary", to_date("1980-02-02")) sub;
SELECT * FROM persons;
SELECT MAX(birthday) FROM persons;

--Select rows that contain the latest date (with condition)
TRUNCATE TABLE persons;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John", to_date("1970-01-01")) sub;
INSERT INTO TABLE persons SELECT * FROM (SELECT "John", to_date("1975-01-01")) sub;
INSERT INTO TABLE persons SELECT * FROM (SELECT "Mary", to_date("1980-02-02")) sub;
SELECT * FROM persons WHERE name='John';
SELECT MAX(birthday) FROM persons WHERE name='John';
SELECT * FROM persons WHERE name='John' AND birthday IN (
    SELECT MAX(birthday) FROM persons p WHERE p.name='John'
);
SELECT birthday FROM persons GROUP BY birthday;
