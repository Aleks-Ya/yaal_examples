# 020-create-aurora-mysql

## Task
Create an Aurora MySQL database and connect to it from CloudShell.

## Steps
1. Create a database
    1. Choose a database creation method: `Easy create`
    2. Engine type: `Aurora (MySQL Compatible)`
    3. DB instance size: `Dev/Test`
    4. DB instance identifier: `kata-db-create-aurora-mysql`
    5. Master username: `admin`
    6. Credentials management: `Self managed`
    7. Master password: `admin123`
2. Connect
    1. Open CloudShell 
    2. Create a VPC environment 
        1. Name: `kata-shell-aurora` (28 symbols limit)
        2. Virtual private cloud (VPC): `default`
        3. Subnet: any
        4. Security group: `default`
    3. Connect: 
        ```shell
        mysql -P 3306 --user=admin --password=admin123 \
            -h kata-db-create-aurora-mysql.cluster-cjsfzeowk6y5.us-east-1.rds.amazonaws.com
        ```
    4. List databases: `show databases;`
    5. Disconnect: `quit`
    
## Cleanup
1. Delte VPC environment
2. Delete Database

## History
- 2026-01-27
