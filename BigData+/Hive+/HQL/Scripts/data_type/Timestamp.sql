--Hive 1.2.2: working with timestamps

-- Show current timestamp
select unix_timestamp();

-- String to timestamp
select unix_timestamp('2020-03-25 16:32:01');

-- String to timestamp (custom pattern)
select unix_timestamp('2020-03-25','yyyy-MM-dd');
select unix_timestamp('16:39','HH:mm');

-- String timestamp to Date
select to_date('2020-03-25 16:32:01');


----- Use timestamp as field type -----
CREATE DATABASE IF NOT EXISTS yaal;
USE yaal;

-- CSV default format
DROP TABLE IF EXISTS events;
CREATE TABLE events (
    id INT,
    moment TIMESTAMP);
INSERT INTO TABLE events VALUES (1, '2020-03-25 16:32:01');
SELECT * FROM events;


-- Parquet format
DROP TABLE IF EXISTS events_parquet;
CREATE TABLE events_parquet (
    id INT,
    moment TIMESTAMP)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
STORED AS Parquet;
INSERT INTO TABLE events_parquet VALUES (1, '2020-03-25 16:32:01');
SELECT * FROM events_parquet;
