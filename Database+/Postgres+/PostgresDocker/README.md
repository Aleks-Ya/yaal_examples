# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```
docker run \
  --name postgres12 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:12
```

## Connect via PSQL
```
docker run -it --rm \
  --link postgres12:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:12 \
  psql -h postgres -U pguser
```

## Run SQL-script from file
```
docker run -it --rm \
  --link postgres12:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:12 \
  psql -h postgres -U pguser -a -f /scripts/my_script.sql
```

## Run pg_dump
```
docker run -it --rm \
  --link postgres12:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:12 \
  pg_dump -h postgres -U pguser
```

## Connect via JDBC
Container IP: `docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres12`
URL: `jdbc:postgresql://172.17.0.2:5432/postgres`
Login: `pguser`
Password: `pgpass`
