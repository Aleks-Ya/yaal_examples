# Run LiquiBase "Quick Start"
Source: https://www.liquibase.org/quickstart.html

## Install LiquiBase
1. Choose LIQUIBASE_HOME, e.g. `/home/aleks/installed/liquibase`
1. Add `export LIQUIBASE_HOME=/home/aleks/installed/liquibase` to `~/.bashrc`
1. Add `export PATH=$PATH:$LIQUIBASE_HOME` to `~/.bashrc`
1. Download ZIP from https://download.liquibase.org/download/?frm=n
1. Unpack ZIP to `$LIQUIBASE_HOME`

## Download Postgres JDBC driver
Download from https://jdbc.postgresql.org/download.html
and save to `$LIQUIBASE_HOME/libs/`

## Download slf4j API jar
Download from https://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar
and save to `$LIQUIBASE_HOME/libs/`

## Run Postgres in Docker
```
docker run \
  --name postgres10 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:10
```

## Create database
Connect via PSQL:

```
docker run -it --rm \
  --link postgres10:postgres postgres psql \
  -h postgres \
  -U pguser
```

Create the database:

`CREATE DATABASE liquidbase_quick_start`

## Execute LiquiBase Changelog File
```
liquibase --driver=org.postgresql.Driver \
     --classpath=/home/aleks/installed/postgres_jdbc/slf4j-api-1.7.25.jar \
     --changeLogFile=changelog.xml \
     --url="jdbc:postgresql://172.17.0.2:5432/liquidbase_quick_start" \
     --username=pguser \
     --password=pgpass \
     migrate
```
