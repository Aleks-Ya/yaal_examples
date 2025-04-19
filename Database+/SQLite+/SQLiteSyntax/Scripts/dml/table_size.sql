--Calculate size of a table in bytes
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id INTEGER,
	name VARCHAR
);
WITH RECURSIVE sequence AS (
  SELECT 1 AS number
  UNION ALL
  SELECT number + 1
  FROM sequence
  WHERE number < 10000
)
INSERT INTO person(id, name) 
	SELECT 
		number AS id, 
		'Name ' || number AS name 
	FROM sequence;

SELECT SUM(pgsize) AS table_size FROM dbstat WHERE name = 'person';