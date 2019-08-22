drop schema if exists not_null cascade;
create schema if not exists not_null;

CREATE TABLE not_null.not_null_products (
    product_no integer NOT NULL,
    name text NOT NULL,
    price numeric
);

-- Full text equivalent of NOT NULL keyword
CREATE TABLE not_null.not_null_products2 (
    product_no integer,
    name text,
    price numeric,
    CHECK (product_no IS NOT NULL),
    CHECK (name IS NOT NULL)
);

-- NOT NULL and another constraint in the same line
CREATE TABLE not_null.not_null_products3 (
    product_no integer NOT NULL,
    name text NOT NULL,
    price numeric NOT NULL CHECK (price > 0)
);
