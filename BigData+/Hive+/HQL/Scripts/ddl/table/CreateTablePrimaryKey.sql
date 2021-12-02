-- CREATE TABLE with primary key
-- Docs: https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DDL/#LanguageManualDDL-CreateTable

--NOT WORK

CREATE DATABASE IF NOT EXISTS people;
USE people;

DROP TABLE IF EXISTS persons;
CREATE TABLE IF NOT EXISTS persons (
  id BIGINT, 
  name STRING,
  age INT,
  PRIMARY KEY(id) DISABLE NOVALIDATE
);

INSERT INTO TABLE persons VALUES
(1, 'Peter', 40),
(2, 'John',  35),
(3, 'Mary',  30); 

SELECT * FROM persons;

-- Violate the primary key restriction
INSERT INTO TABLE persons VALUES (1, 'Mike',  25);

SELECT * FROM persons;
