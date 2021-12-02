-- SELECT statement
-- Docs: https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Select

CREATE DATABASE IF NOT EXISTS people;
USE people;

DROP TABLE IF EXISTS persons;
CREATE TABLE IF NOT EXISTS persons (
  id BIGINT,
  name STRING,
  age INT
);

INSERT INTO TABLE persons VALUES
(1, 'Peter', 40),
(2, 'John',  35),
(3, 'Mary',  30),
(4, 'Mike',  25),
(5, 'Rick',  20),
(6, 'Nick',  15);

SELECT * FROM persons;

-- Where string column contains a value
SELECT * FROM persons WHERE instr(name,'ic') > 0;