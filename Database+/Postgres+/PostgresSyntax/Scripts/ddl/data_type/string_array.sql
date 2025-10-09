CREATE TABLE string_array_table (names varchar[]);
INSERT INTO string_array_table VALUES (ARRAY['alice', 'bob']);
SELECT names, array_to_json(names) FROM string_array_table WHERE 'alice' = ANY(names);
DROP TABLE IF EXISTS string_array_table;