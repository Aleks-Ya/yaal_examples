# Athena SQL

Create database: `CREATE DATABASE people_database`

Create table based on folder with CSV files (with header):
```
CREATE EXTERNAL TABLE IF NOT EXISTS people(
    id INT,
    name STRING,
    gender STRING,
    age INT
)
ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
LOCATION 's3://yaal-test-bucket/athena_people/source/'
TBLPROPERTIES ("skip.header.line.count"="1");
```

Select from a table: `SELECT * FROM "people_database"."people" where age >= 18;`

Drop a table: `DROP TABLE people;`

Using quotes: 
```
DROP TABLE `people`;
```
