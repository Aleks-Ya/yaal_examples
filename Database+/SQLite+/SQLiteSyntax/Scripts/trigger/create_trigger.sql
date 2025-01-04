--Create a trigger that log inserts into another table

--Create data table
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id INTEGER,
	name VARCHAR
);

--Create event table
DROP TABLE IF EXISTS events;
CREATE TABLE events (
	id INTEGER,
	details VARCHAR
);

--Create a trigger
DROP TRIGGER IF EXISTS person_insert;
CREATE TRIGGER person_insert 
AFTER INSERT ON person 
FOR EACH ROW 
BEGIN 
	INSERT INTO events VALUES(NEW.id, NEW.name);
END;

--Test trigger
INSERT INTO person(id, name) VALUES (1, 'John'), (2, 'Mary');
SELECT * FROM events;