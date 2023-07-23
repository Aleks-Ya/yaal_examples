Create table If Not Exists examples.Person (Id int, Email varchar(255));
insert into examples.Person (id, email) values ('1', 'john@example.com');
insert into examples.Person (id, email) values ('2', 'bob@example.com');
insert into examples.Person (id, email) values ('3', 'john@example.com');

EXPLAIN DELETE FROM examples.Person WHERE id NOT IN (SELECT * FROM (SELECT MIN(id) FROM Person GROUP BY email) AS tmp);

EXPLAIN delete p1 from examples.Person p1, examples.Person p2 where p1.email=p2.email and p1.id>p2.id;
