drop schema if exists many_to_many cascade;
create schema if not exists many_to_many;

CREATE TABLE many_to_many.products (
    product_no integer PRIMARY KEY,
    name text,
    price numeric
);

CREATE TABLE many_to_many.orders (
    order_id integer PRIMARY KEY,
    shipping_address text,
    ...
);

CREATE TABLE many_to_many.order_items (
    product_no integer REFERENCES products,
    order_id integer REFERENCES orders,
    quantity integer,
    PRIMARY KEY (product_no, order_id)
);
