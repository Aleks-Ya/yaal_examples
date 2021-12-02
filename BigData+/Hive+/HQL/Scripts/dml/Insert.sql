-- INSERT statement
-- Docs: https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DML

CREATE DATABASE IF NOT EXISTS people;
USE people;

DROP TABLE IF EXISTS persons;
CREATE TABLE IF NOT EXISTS persons (
  id INT,
  fio STRING,
  age INT
);

--Insert single row
INSERT INTO TABLE persons VALUES (1, 'Peter', 40);

--Insert multiple rows
INSERT INTO TABLE persons VALUES
(2, 'John', 35),
(3, 'Mary', 30),
(4, 'Mike', 25);

--Insert multiple rows with column names
INSERT INTO TABLE persons(fio, age, id) VALUES
('Rick', 20, 5),
('Nick', 15, 6);

SELECT * FROM persons;