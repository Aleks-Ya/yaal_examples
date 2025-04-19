--Create a table
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id INTEGER,
	name VARCHAR
);
INSERT INTO person(id, name) VALUES (1, 'John'), (2, 'Mary');
SELECT * FROM person;
