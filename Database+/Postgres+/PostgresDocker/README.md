# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```shell
docker run --rm \
  --name postgres17 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  postgres:17
```

## Check connection with telnet
```shell
export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres17)
telnet $POSTGRES_IP 5432
```

## Connect with PSQL
### From another container
```shell
docker run -it --rm \
  --link postgres17:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:13 \
  psql -h postgres -U pguser
```

### From host OS
Install PSQL: `sudo apt install -y postgresql-client-12`
Connect:
```shell
export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres17)
export PGPASSWORD=pgpass
psql -h $POSTGRES_IP -p 5432 -U pguser -d postgres
```


## Execute single command
```shell
docker run -it --rm \
  --link postgres17:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  psql -h postgres -U pguser -c 'CREATE DATABASE my_test_db'
```

## Run SQL-script from file
```shell
docker run -it --rm \
  --link postgres17:postgres \
  --env PGPASSWORD="pgpass" \
  -v $PWD:/scripts \
  postgres:13 \
  psql -h postgres -U pguser -a -f /scripts/my_script.sql
```

## Run pg_dump
```shell
docker run -it --rm \
  --link postgres17:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:13 \
  pg_dump -h postgres -U pguser
```

## Connect via JDBC
Container IP: `export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres17)`
URL: `jdbc:postgresql://${POSTGRES_IP}:5432/postgres`
Login: `pguser`
Password: `pgpass`
