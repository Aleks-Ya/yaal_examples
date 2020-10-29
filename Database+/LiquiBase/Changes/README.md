# Using LiguiBase commands

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

## 2. Create "commands" database
```
docker run -it --rm \
  --link postgres-liquibase:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  psql -h postgres -U pguser -c 'CREATE DATABASE commands'
```

## 3. Execute commands
### Migrate
```
liquibase \
    --classpath=../postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master.xml \
    --url="jdbc:postgresql://${IP}:5432/commands" \
    --username=pguser \
    --password=pgpass \
    migrate
```

### Update
```
liquibase \
    --classpath=../postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master.xml \
    --url="jdbc:postgresql://172.17.0.2:5432/commands" \
    --username=pguser \
    --password=pgpass \
    update
```

### Generate ChangeLog
```
liquibase \
    --classpath=../postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master2.xml \
    --url="jdbc:postgresql://172.17.0.2:5432/commands" \
    --username=pguser \
    --password=pgpass \
    generateChangeLog
```

### Generate SQL script
```
liquibase \
    --classpath=../postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master.xml \
    --url="jdbc:postgresql://172.17.0.2:5432/commands" \
    --username=pguser \
    --password=pgpass \
    updateSQL
```


### Status (show number of not executed Change Sets)
```
liquibase \
    --classpath=../postgresql-42.2.18.jar \
    --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master.xml \
    --url="jdbc:postgresql://172.17.0.2:5432/commands" \
    --username=pguser \
    --password=pgpass \
    status
```
