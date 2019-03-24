drop schema if exists create_index cascade;
create schema if not exists create_index;

CREATE TABLE create_index.test1 (
    id integer,
    content varchar
);

SELECT content FROM create_index.test1 WHERE id = 1;

-- Delete index
drop index create_index.test1_id_index;

-- Check that index is used in a quiery
explain SELECT content FROM create_index.test1 WHERE id = 1;

-- Create B-tree index
CREATE INDEX test1_id_index ON create_index.test1 (id);

-- Create a Hash index
CREATE INDEX name ON table USING HASH (column);

