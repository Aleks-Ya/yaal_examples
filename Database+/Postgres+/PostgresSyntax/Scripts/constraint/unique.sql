drop schema if exists unique_constraint cascade;
create schema if not exists unique_constraint;

-- Add UNIQUE constraint (as a column constraint)
CREATE TABLE unique_constraint.products1 (
    product_no integer UNIQUE,
    name text,
    price numeric
);

-- Add UNIQUE constraint (as a table constraint)
CREATE TABLE unique_constraint.products2 (
    product_no integer,
    name text,
    price numeric,
    UNIQUE (product_no)
);

-- Add UNIQUE constraint for several columns
CREATE TABLE unique_constraint.products3 (
    a integer,
    b integer,
    c integer,
    UNIQUE (a, c)
);

--  Add UNIQUE constraint (with name)
CREATE TABLE unique_constraint.products4 (
    product_no integer CONSTRAINT must_be_different UNIQUE,
    name text,
    price numeric
);