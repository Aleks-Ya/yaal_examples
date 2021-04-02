CREATE SCHEMA syntax2;

SELECT name, database_id, create_date FROM sys.databases;

SELECT name, schema_id FROM sys.schemas;

SELECT s.name AS schema_name, s.schema_id, u.name AS schema_owner FROM sys.schemas s INNER JOIN sys.sysusers u ON u.uid = s.principal_id;