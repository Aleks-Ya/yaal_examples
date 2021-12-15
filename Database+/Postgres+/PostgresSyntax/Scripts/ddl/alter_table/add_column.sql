-- Add new column to an existing table

DROP SCHEMA IF EXISTS add_column CASCADE;
CREATE SCHEMA IF NOT EXISTS add_column;

CREATE TABLE add_column.people(
	id INTEGER PRIMARY KEY,
	name VARCHAR
);

INSERT INTO add_column.people(id, name) VALUES (1, 'John'), (2, 'Mary');

SELECT * FROM add_column.people;

ALTER TABLE add_column.people ADD COLUMN married BOOLEAN;
ALTER TABLE add_column.people ALTER COLUMN married SET DEFAULT FALSE;
UPDATE add_column.people SET married=FALSE WHERE married IS NULL;

INSERT INTO add_column.people(id, name, married) VALUES (3, 'Peter', TRUE);

SELECT * FROM add_column.people;
