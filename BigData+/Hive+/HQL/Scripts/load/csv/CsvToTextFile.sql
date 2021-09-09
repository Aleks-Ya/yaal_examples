-- Load CSV into Hive table stored as TextFile

CREATE DATABASE IF NOT EXISTS yaal;
USE yaal;

DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee (
	 id int,
	 name string,
	 age int,
	 gender string)
 COMMENT 'Employee Table'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 STORED AS TextFile;

SELECT * from employee;
 
-- Load CSV to HDFS
--docker cp employee.csv hive-yarn-hdfs-master:/tmp/employee.csv
--docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -put /tmp/employee.csv /tmp/employee.csv'
--docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -chmod -R a+w /tmp'

LOAD DATA INPATH '/tmp/employee.csv' INTO TABLE employee;

SELECT * FROM employee;

-- Result in HDFS: /user/hive/warehouse/yaal.db/employee/employee.csv
