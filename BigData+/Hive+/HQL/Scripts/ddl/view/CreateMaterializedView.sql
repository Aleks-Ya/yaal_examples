-- CREATE VIEW (materialized)
-- Docs: https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DDL#LanguageManualDDL-Create/Drop/AlterMaterializedView

CREATE DATABASE IF NOT EXISTS people;
USE people;

DROP TABLE IF EXISTS persons;
CREATE TABLE IF NOT EXISTS persons (
  id BIGINT, 
  name STRING,
  age INT
);

INSERT INTO TABLE persons VALUES
(1, 'Peter', 35),
(2, 'John',  30),
(3, 'Mary',  25),
(4, 'Mike',  20),
(5, 'Rick',  15),
(6, 'Nick',  10);

DROP VIEW IF EXISTS adult_persons;
CREATE MATERIALIZED VIEW IF NOT EXISTS adult_persons AS SELECT * FROM persons WHERE age > 18;

SELECT * FROM adult_persons;
