-- Load CSV into Hive table

create database CSV_TO_HIVE;
USE CSV_TO_HIVE;

CREATE TABLE IF NOT EXISTS employee (
	 id int,
	 name string,
	 age int,
	 gender string)
 COMMENT 'Employee Table'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ',';

SELECT * from employee;
 
-- Load CSV to HDFS
--docker cp employee.csv hive-yarn-hdfs-master:/tmp/employee.csv
--docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -put /tmp/employee.csv /tmp/employee.csv'
--docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -chmod -R a+w /tmp'

LOAD DATA INPATH '/tmp/employee.csv' INTO TABLE employee;

SELECT * from employee;