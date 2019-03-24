drop schema if exists foreign_key cascade;
create schema if not exists foreign_key;

-- Referenced table
CREATE TABLE foreign_key.products (
    product_no integer PRIMARY KEY,
    name text,
    price numeric
);

-- Add foreign key constraint
CREATE TABLE foreign_key.orders (
    order_id integer PRIMARY KEY,
    product_no integer REFERENCES foreign_key.products (product_no),
    quantity integer
);

-- Add foreign key constraint (short form)
CREATE TABLE foreign_key.orders2 (
    order_id integer PRIMARY KEY,
    product_no integer REFERENCES foreign_key.products,
    quantity integer
);

-- Add foreign key to a group of columns 
CREATE TABLE foreign_key.products2 (
    product_no integer,
    name text,
    price numeric,
    primary key(product_no, name)
);
CREATE TABLE foreign_key.orders3 (
    order_id integer PRIMARY KEY,
    product_no integer,
    product_name text,
    FOREIGN KEY (product_no, product_name) REFERENCES foreign_key.products2(product_no, name)
);

-- List constraints
select * from information_schema.table_constraints where constraint_schema = 'foreign_key';