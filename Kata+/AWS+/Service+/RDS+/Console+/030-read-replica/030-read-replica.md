# 030-read-replica

## Task
Create an RDS database with a Read Replica. Connect to both the Primary Replica and the Read Replica. 

## Steps
1. Create a database
    1. Choose a database creation method: `Easy create`
    2. Engine type: `Aurora (PostgreSQL Compatible)`
    3. DB instance size: `Dev/Test`
    4. DB instance identifier: `kata-db-read-replica`
    5. Master username: `postgres`
    6. Credentials management: `Self managed`
    7. Master password: `postgres1`
2. Connect
    1. Open CloudShell 
    2. Create a VPC environment 
        1. Name: `kata-shell-read-replica` (28 symbols limit)
        2. Virtual private cloud (VPC): `default`
        3. Subnet: any
        4. Security group: `default`
    3. Connect to primary replica:
    	1. Connect:
        	```shell
        	PGPASSWORD=postgres1 psql -p 5432 -U postgres \
        	-h kata-db-read-replica-instance-1.cjsfzeowk6y5.us-east-1.rds.amazonaws.com
        	```
        2. Create a database: `CREATE DATABASE mydb;`
        3. Connect to the database: `\connect mydb`
        4. Create a table: `CREATE TABLE cities (city text);`
        5. Insert a row: `INSERT INTO cities VALUES ('London');`
        6. Select all rows: `SELECT * from cities;`
		7. Disconnect: `\q`
3. Create a Read Replica:
	1. Actions - Add Reader
		1. DB instance identifier: `kata-replica-read-replica`
		2. DB instance class: `Serverless v2`
		3. Public access: `Publicly accessible`
	2. Connect to read reaplica:
    	1. In the VPC enrivonment
    	2. Connect:
        	```shell
        	PGPASSWORD=postgres1 psql -p 5432 -U postgres -h kata-replica-read-replica.cjsfzeowk6y5.us-east-1.rds.amazonaws.com
        	```
        3. Connect to the database: `\connect mydb`
		4. Select all rows: `SELECT * from cities;`
		5. Try to insert a row (should fail): `INSERT INTO cities VALUES ('Paris');`
		6. Disconnect: `\q`

## Cleanup
1. Delte VPC environment
2. Delte the Database
	1. Delete the Read Replica
	2. Delete the Primary Replica
	3. Delete the Cluster

## History
- 2026-02-10 success
