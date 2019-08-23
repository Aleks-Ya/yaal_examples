-- Covered Indexes
-- Documentation: https://phoenix.apache.org/secondary_indexing.html

DROP TABLE IF EXISTS person;
CREATE TABLE person (
    id INTEGER PRIMARY KEY,
    name VARCHAR,
    surname VARCHAR,
    age INTEGER
);

UPSERT INTO person(id, name, surname, age) VALUES(1, 'Jonh', 'Will', 25);
UPSERT INTO person(id, name, surname, age) VALUES(2, 'Mary', 'Smith', 20);
UPSERT INTO person(id, name, surname, age) VALUES(3, 'Mike', 'Grey', 30);

SELECT * FROM person;

-- No indexes
EXPLAIN SELECT * FROM person WHERE name = 'Mike';

-- Index on single column
DROP INDEX IF EXISTS person_name_index_1 ON person;
CREATE INDEX person_name_index_1 ON person(name);
EXPLAIN SELECT name FROM person WHERE name = 'Mike';

-- Index on two columns
DROP INDEX IF EXISTS person_name_index_2 ON person;
CREATE INDEX person_name_index_2 ON person(name, age);
EXPLAIN SELECT name, age FROM person WHERE name = 'Mike' AND age > 25;

-- Index on two columns (including additional column)
DROP INDEX IF EXISTS person_name_index_3 ON person;
CREATE INDEX person_name_index_3 ON person(name, age) INCLUDE (surname);
EXPLAIN SELECT name, surname, age FROM person WHERE name = 'Mike' AND age > 25;
