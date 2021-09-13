-- Load CSV into Hive table stored as Parquet

CREATE DATABASE IF NOT EXISTS yaal;
USE yaal;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS temp;

-- Load CSV to HDFS
-- docker cp employee.csv hive-yarn-hdfs-master:/tmp/employee.csv
-- docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -mkdir /tmp/csv'
-- docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -put /tmp/employee.csv /tmp/csv/employee.csv'
-- docker exec -it hive-yarn-hdfs-master su hdfs -c 'hdfs dfs -chmod -R a+w /tmp'

CREATE TEMPORARY EXTERNAL TABLE IF NOT EXISTS temp (
	 id int,
	 name string,
	 age int,
	 gender string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'hdfs://master-service:8020/tmp/csv';

SELECT * from temp;

CREATE TABLE IF NOT EXISTS employee (
	 id int,
	 name string,
	 age int,
	 gender string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS PARQUET;

INSERT INTO TABLE employee SELECT * FROM temp;

SELECT * FROM employee;

-- Result in HDFS: /user/hive/warehouse/yaal.db/employee/000000_0
