drop schema if exists exclude_constraint cascade;
create schema if not exists exclude_constraint;

-- List constraints
select * from information_schema.table_constraints where table_schema = 'exclude_constraint';

CREATE TABLE exclude_constraint.circles (
    c circle,
    EXCLUDE USING gist (c WITH &&)
);