# SQL Server commands
To execute entered commands use command `go`. E.g.:
```
EXEC sp_databases
go
```

## Complete object name
`schema_name` is the owner name.

Fully qualified name (have to be unique): `server_name.[database_name].[schema_name].object_name`

Common form:
```
server_name.[database_name].[schema_name].object_name
| database_name.[schema_name].object_name
| schema_name.object_name
| object_name
```
Examples:
```
server_name.database_name.schema_name.object_name
server_name.database_name..object_name
server_name..schema_name.object_name
server_name...object_name
database_name.schema_name.object_name
database_name..object_name
schema_name.object_name
object_name
```
