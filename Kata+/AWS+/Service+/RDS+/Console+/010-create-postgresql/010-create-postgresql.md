# 010-create-postgresql

## Task
Create an RDS Postgres database and connect to it from CloudShell.

## Steps
1. Create a database
    1. Choose a database creation method: `Easy create`
    2. Engine type: `PostgreSQL`
    3. DB instance size: `Free tier`
    4. DB instance identifier: `kata-db-create-postgresql`
    5. Master username: `postgres`
    6. Credentials management: `Self managed`
    7. Master password: `postgres1`
2. Connect
    1. Open CloudShell 
    2. Create a VPC environment 
        1. Name: `kata-shell-postgresql` (28 symbols limit)
        2. Virtual private cloud (VPC): `default`
        3. Subnet: any
        4. Security group: `default`
    3. Connect: 
        ```shell
        PGPASSWORD=postgres1 psql -p 5432 -U postgres \
            -h kata-db-create-postgresql.cjsfzeowk6y5.us-east-1.rds.amazonaws.com
        ```
    4. List schemas: `\dn`
    5. Disconnect: `\q`
    
## Cleanup
1. Delte VPC environment
2. Delete Database

## History
- 2026-01-27
