drop schema if exists view_from_select cascade;
create schema if not exists view_from_select;

CREATE TABLE view_from_select.products (
    id integer,
    name varchar
);

insert into view_from_select.products values(1, 'car'), (2, 'airplane'), (3, 'train');
select * from view_from_select.products;

create view view_from_select.view1 as select * from view_from_select.products where id > 2;
select * from view_from_select.view1;