drop schema if exists delete_duplicates cascade;
create schema if not exists delete_duplicates;

CREATE TABLE delete_duplicates.products (
    id integer,
    content varchar
);

insert into delete_duplicates.products values(1, 'car'), (1, 'car');
insert into delete_duplicates.products values(2, 'plane');
select * from delete_duplicates.products;

-- Find duplicates
select * from delete_duplicates.products group by id, content having count(*) > 1;
