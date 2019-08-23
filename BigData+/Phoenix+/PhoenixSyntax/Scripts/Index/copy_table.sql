-- Copy table in the same database

DROP TABLE IF EXISTS source_table;
CREATE TABLE source_table (
	id INTEGER PRIMARY KEY,
	text VARCHAR
);

DROP TABLE IF EXISTS dest_table;
CREATE TABLE dest_table (
	id INTEGER PRIMARY KEY,
	text VARCHAR
);

UPSERT INTO source_table(id, text) VALUES (1, 'abc');
UPSERT INTO source_table(id, text) VALUES (2, 'def');

SELECT * FROM source_table;
SELECT * FROM dest_table;

-- Declare column names
UPSERT INTO dest_table(id, text) SELECT id, text FROM source_table;
-- Use *
--UPSERT INTO dest_table SELECT * FROM source_table;


SELECT * FROM dest_table;



