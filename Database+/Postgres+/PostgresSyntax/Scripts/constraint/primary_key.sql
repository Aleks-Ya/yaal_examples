drop schema if exists primary_key cascade;
create schema if not exists primary_key;

-- List constraints in a table
select * from information_schema.table_constraints where table_schema = 'primary_key';

-- add primary key constraint 
CREATE TABLE primary_key.products (
    product_no integer PRIMARY KEY,
    name text,
    price numeric
);

-- Add primary key constraint on several columns
CREATE TABLE primary_key.example (
    a integer,
    b integer,
    c integer,
    PRIMARY KEY (a, c)
);

-- Add primary key to exist table
CREATE TABLE primary_key.products2 (
    product_no integer,
    name text
);
alter table primary_key.products2 add primary key (product_no);

