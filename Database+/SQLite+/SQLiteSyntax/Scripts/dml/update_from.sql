--Use UPDATE FROM query (SQLite version >=3.33.0)
--Select and increment a numeic field
--Task: Increment "repetitions" field for enabled counters.
--Docs: https://www.sqlite.org/lang_update.html

--Prepare
DROP TABLE IF EXISTS counter;
CREATE TABLE counter(
	id INTEGER,
	repetitions INTEGER,
	enabled INTEGER
);
INSERT INTO counter(id, repetitions, enabled) VALUES (1, 10, 1), (2, 20, 0), (3, 30, 1);
SELECT * FROM counter;

--Increment
UPDATE counter
  SET repetitions = s.repetitions + 1
  FROM (SELECT id, repetitions, enabled FROM counter WHERE enabled=1) AS s
  WHERE counter.id = s.id;
SELECT * FROM counter;
