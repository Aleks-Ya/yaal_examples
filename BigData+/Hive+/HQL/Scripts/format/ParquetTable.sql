
-- Create a Hive table stored as a Parquet file

CREATE DATABASE IF NOT EXISTS iablokov_test;

USE iablokov_test;

DROP TABLE IF EXISTS employee_parquet;

create table employee_parquet(
	id int,
	name string,
	salary int,
	deptno int) 
row format delimited fields terminated by ',' 
stored as Parquet ;

INSERT INTO employee_parquet(id, name, salary, deptno) VALUES(1, 'John', 1000000, 33);
INSERT INTO employee_parquet(id, name, salary, deptno) VALUES(2, 'Mary', 100000, 44);

SELECT * FROM employee_parquet;

-- File location in HDFS: /user/hive/warehouse/iablokov_test.db/employee_parquet/000000_0
-- docker exec -it hive-yarn-hdfs-master hdfs dfs -ls /user/hive/warehouse/iablokov_test.db/employee_parquet
