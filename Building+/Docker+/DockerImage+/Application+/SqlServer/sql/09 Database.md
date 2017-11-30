# Database

## Show databases
`EXEC sp_databases`
or
`SELECT name FROM master.dbo.sysdatabases`

## Show current database
`SELECT db_name()`

## Show database owner
`select suser_sname(owner_sid) from sys.databases where name = 'db1'`
