--Insert from select
DROP TABLE IF EXISTS T1;
DROP TABLE IF EXISTS T2;
CREATE TABLE T1(
	id NUMERIC,
	title VARCHAR 
);
CREATE TABLE T2(
	identifier NUMERIC,
	name VARCHAR 
);
INSERT INTO T1(id, title) VALUES (1, 'AAA'), (2, 'BBB');
INSERT INTO T2(identifier, name) SELECT id, title FROM T1;
SELECT * FROM T2;
