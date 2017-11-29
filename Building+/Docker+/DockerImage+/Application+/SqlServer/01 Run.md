# Run SQL Server 2017

## Run:
`docker run --name sql-server -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=thePassw0rd_' -p 1433:1433 -d microsoft/mssql-server-linux:2017-CU1`

## Connect:
```
docker exec -it sql-server bash
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P 'thePassw0rd_'
```
