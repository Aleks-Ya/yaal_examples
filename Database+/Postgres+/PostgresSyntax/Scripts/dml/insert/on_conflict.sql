drop schema if exists on_conflict cascade;
create schema if not exists on_conflict;

CREATE TABLE on_conflict.products (
    product_no integer primary key,
    name text
);


-- Do update
insert into on_conflict.products values (1, 'car');
insert into on_conflict.products values (1, 'airplane') 
on conflict(product_no) do update set name = EXCLUDED.name;

-- Do nothing
insert into on_conflict.products values (2, 'piano');
insert into on_conflict.products values (2, 'guitar') 
on conflict(product_no) do nothing;


select * from on_conflict.products;