# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```
docker run \
  --name postgres11 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:11
```

## Connect via PSQL
```
docker run -it --rm \
  --link postgres11:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:11 \
  psql -h postgres -U pguser
```

## Run pg_dump
```
docker run -it --rm \
  --link postgres11:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:11 \
  pg_dump -h postgres -U pguser
```

## Connect via JDBC
Container IP: `docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres11`
URL: `jdbc:postgresql://172.17.0.2:5432/postgres`
Login: `pguser`
Password: `pgpass`
