
-- Create a Hive table stored as a text file

CREATE DATABASE IF NOT EXISTS yaal;

USE yaal;

DROP TABLE IF EXISTS employee_textfile;

create table employee_textfile(
	id int,
	name string,
	salary int,
	deptno int) 
row format delimited fields terminated by ',' 
stored as TextFile ;

INSERT INTO employee_textfile(id, name, salary, deptno) VALUES(1, 'John', 1000000, 33);
INSERT INTO employee_textfile(id, name, salary, deptno) VALUES(2, 'Mary', 100000, 44);

SELECT * FROM employee_textfile;

-- File location in HDFS: /user/hive/warehouse/yaal.db/employee_textfile/000000_0
-- docker exec -it hive-yarn-hdfs-master hdfs dfs -ls /user/hive/warehouse/yaal.db/employee_textfile
