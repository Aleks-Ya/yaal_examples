
-- Create a Hive table stored as a Parquet file

CREATE DATABASE IF NOT EXISTS yaal;

USE yaal;

DROP TABLE IF EXISTS employee_parquet;

CREATE TABLE employee_parquet(
	id INT,
	name string,
	salary INT,
	deptno INT) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
STORED AS Parquet;

INSERT INTO employee_parquet(id, name, salary, deptno) VALUES(1, 'John', 1000000, 33);
INSERT INTO employee_parquet(id, name, salary, deptno) VALUES(2, 'Mary', 100000, 44);

SELECT * FROM employee_parquet;

-- File location in HDFS: /user/hive/warehouse/yaaldb/employee_parquet/000000_0
-- docker exec -it hive-yarn-hdfs-master hdfs dfs -ls /user/hive/warehouse/yaal.db/employee_parquet
