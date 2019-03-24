drop schema if exists temp_view cascade;
create schema if not exists temp_view;

CREATE TABLE temp_view.products (
    id integer,
    name varchar
);

insert into temp_view.products values(1, 'car'), (2, 'airplane'), (3, 'train');
select * from temp_view.products;

create temp view view_tmp as select * from view_from_select.products where id > 2;
select * from view_tmp;