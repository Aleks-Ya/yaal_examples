--Simple
SELECT 'SQLite ' || 'CONCAT';


--In select
DROP TABLE IF EXISTS people;
CREATE TABLE people(
	id INTEGER,
	name TEXT
);
INSERT INTO people(id, name) VALUES (1, 'John'), (2, 'Mary');
SELECT * FROM people;
SELECT name || '-' || id AS title FROM people;
