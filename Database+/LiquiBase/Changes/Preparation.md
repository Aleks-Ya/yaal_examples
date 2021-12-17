# Prepare Postgres for using LiquiBase

## 1. Run Postgres 
### 1.1. Run Postgres in Docker:
```
docker run --rm \
  --name postgres-liquibase \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:13
```
### 1.2. Create env variable with Postgres IP address:
`export IP=$(docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres-liquibase)`

## 2. Re-create "commands" database
```
docker run -it --rm \
  --link postgres-liquibase:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  bash -c "psql -h postgres -U pguser -c 'DROP DATABASE IF EXISTS commands' && psql -h postgres -U pguser -c 'CREATE DATABASE commands'"
```

## 3. Execute commands
### Create Linux alias
Works:
```
alias liquibase_postgres='
liquibase \
    --classpath=/home/aleks/pr/home/yaal_examples/Database+/LiquiBase/postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --url="jdbc:postgresql://${IP}:5432/commands" \
    --username=pguser \
    --password=pgpass'
```

Env variables don't work (`The option --url is required.`):
```
export LIQUIBASE_CLASSPATH=../postgresql-42.2.18.jar
export LIQUIBASE_DRIVER=org.postgresql.Driver
export LIQUIBASE_COMMAND_URL=jdbc:postgresql://${IP}:5432/commands
export LIQUIBASE_COMMAND_USERNAME=pguser
export LIQUIBASE_COMMAND_PASSWORD=pgpass
```

`liquibase.properties` doesn't work (can't resolve Docker's `${IP}`):
```
classpath=../postgresql-42.2.18.jar
driver=org.postgresql.Driver
url=jdbc:postgresql://${IP}:5432/commands
username=pguser
password=pgpass
```
