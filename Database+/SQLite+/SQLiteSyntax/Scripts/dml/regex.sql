DROP TABLE IF EXISTS people;
CREATE TABLE people(
	id INTEGER,
	name TEXT
);
INSERT INTO people(id, name) VALUES (1, 'John'), (2, 'Mary'), (3, 'Jack');
SELECT * FROM people WHERE name REGEXP 'J.*';

