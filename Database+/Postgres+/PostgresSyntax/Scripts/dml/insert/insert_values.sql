--Insert several values
DROP TABLE IF EXISTS T1;
CREATE TABLE T1(
	id NUMERIC,
	title VARCHAR 
);
INSERT INTO T1 (id, title) VALUES (1, 'AAA'), (2, 'BBB');
SELECT * FROM T1;