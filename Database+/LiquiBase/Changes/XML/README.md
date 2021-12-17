# Using LiquiBase commands in XML format

## 1. Run Postgres
Prerequisites: run Postgres in Docker (`Changes/Preparation.md`)

## 2. Execute commands
### Migrate
`liquibase_postgres --changeLogFile=db.changelog-master.xml migrate`

### Update
`liquibase_postgres --changeLogFile=db.changelog-master.xml update`

### Generate ChangeLog
`liquibase_postgres --changeLogFile=db.changelog-master2.xml generateChangeLog`

### Generate SQL script
`liquibase_postgres --changeLogFile=db.changelog-master.xml updateSQL`

### Status (show number of not executed Change Sets)
`liquibase_postgres --changeLogFile=db.changelog-master.xml status`

### History
`liquibase_postgres --changeLogFile=db.changelog-master.xml history`

### Check sum
Calculate check sum:
`liquibase_postgres --changeLogFile=db.changelog-master.xml calculateCheckSum db.changelog-insert.xml::insert-example::liquibase-docs`
Clear check sums:
`liquibase_postgres --changeLogFile=db.changelog-master.xml clearCheckSums`
