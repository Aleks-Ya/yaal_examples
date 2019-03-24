drop schema if exists logical cascade;
create schema if not exists logical;

-- AND expression
CREATE TABLE logical.products10 (
    product_no integer,
    name text,
    price numeric CHECK (price > 0),
    discounted_price numeric,
    CHECK (discounted_price > 0 AND price > discounted_price)
);