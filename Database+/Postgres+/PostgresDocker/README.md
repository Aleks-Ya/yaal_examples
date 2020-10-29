# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```
docker run \
  --name postgres13 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:13
```

## Connect via PSQL
```
docker run -it --rm \
  --link postgres13:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:13 \
  psql -h postgres -U pguser
```

## Execute single command
```
docker run -it --rm \
  --link postgres13:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  psql -h postgres -U pguser -c 'CREATE DATABASE my_test_db'
```

## Run SQL-script from file
```
docker run -it --rm \
  --link postgres13:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  psql -h postgres -U pguser -a -f /scripts/my_script.sql
```

## Run pg_dump
```
docker run -it --rm \
  --link postgres13:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:13 \
  pg_dump -h postgres -U pguser
```

## Connect via JDBC
Container IP: `docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres13`
URL: `jdbc:postgresql://172.17.0.2:5432/postgres`
Login: `pguser`
Password: `pgpass`
