drop schema if exists check_constraint cascade;
create schema if not exists check_constraint;

-- List constraints
select * from information_schema.table_constraints where table_schema = 'check_constraint';


-- Add constraint without name (name is generated)
CREATE TABLE check_constraint.products (
    product_no integer,
    name text,
    price numeric CHECK (price > 0)
);
-- Violate the constraint
insert into check_constraint.products (product_no, name, price) values (1, 'car', -100);


-- Add constraint with name
CREATE TABLE check_constraint.products2 (
    product_no integer,
    name text,
    price numeric CONSTRAINT positive_price CHECK (price > 0)
);
-- Violate the constraint
insert into check_constraint.products2 (product_no, name, price) values (1, 'car', -100);


-- Add a multi column constraint
CREATE TABLE check_constraint.products3 (
    product_no integer,
    name text,
    price numeric CHECK (price > 0),
    discounted_price numeric CHECK (discounted_price > 0),
    CHECK (price > discounted_price)
);
-- Violate the constraint
insert into check_constraint.products3 (product_no, name, price, discounted_price) values (1, 'car', 100, 200);

-- Add a multi column constraint (with name)
CREATE TABLE check_constraint.products4 (
    product_no integer,
    name text,
    price numeric CHECK (price > 0),
    discounted_price numeric CHECK (discounted_price > 0),
    constraint discount_price_less_price CHECK (price > discounted_price)
);
-- Violate the constraint
insert into check_constraint.products4 (product_no, name, price, discounted_price) values (1, 'car', 100, 200);
