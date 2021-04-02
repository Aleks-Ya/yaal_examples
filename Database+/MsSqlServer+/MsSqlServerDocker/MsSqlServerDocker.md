# Microsoft SQL Server with Docker

Image: https://hub.docker.com/_/microsoft-mssql-server
Run: `docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=my_Pass18' -p 1433:1433 --name mssql mcr.microsoft.com/mssql/server`

## sqlcmd
Run sqlcmd: `docker exec -it mssql /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P my_Pass18`
Execute a query:
```
1> SELECT name, database_id, create_date FROM sys.databases; GO;
2> GO
```

## JDBC
Login: `sa`
Password: `my_Pass18`
Driver: `com.microsoft.sqlserver:mssql-jdbc:9.2.1.jre11`
Docs: https://docs.microsoft.com/en-us/sql/connect/jdbc/overview-of-the-jdbc-driver?view=sql-server-ver15