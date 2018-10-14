# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```
docker run \
  --name postgres10 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:10
```

## Connect via PSQL
```
docker run -it --rm \
  --link postgres10:postgres postgres psql \
  -h postgres \
  -U pguser
```

## Connect via JDBC
URL: `jdbc:postgresql://172.17.0.2:5432/postgres`
Login: `pguser`
Password: `pgpass`
