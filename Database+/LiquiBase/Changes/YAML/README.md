# Using LiquiBase commands in YAML format

## 1. Run Postgres
Prerequisites: run Postgres in Docker (`Changes/Preparation.md`)

## 2. Execute commands
### Migrate
`liquibase_postgres --changeLogFile=db.changelog-master.yaml migrate`

### Update
`liquibase_postgres --changeLogFile=db.changelog-master.yaml update`

### Rollback
Preview rollback SQL (dry-run): `liquibase_postgres --changeLogFile=db.changelog-master.yaml rollbackCountSQL 2`
Revert last 2 change sets: `liquibase_postgres --changeLogFile=db.changelog-master.yaml rollbackCount 2`

### Tag
Set tag: `liquibase_postgres --changeLogFile=db.changelog-master.yaml tag version1`
Check if tag exists: `liquibase_postgres --changeLogFile=db.changelog-master.yaml tagExists version1`

### Status (show number of not executed Change Sets)
`liquibase_postgres --changeLogFile=db.changelog-master.yaml status`

### History
`liquibase_postgres --changeLogFile=db.changelog-master.yaml history`
