# Run Postgres in Docker

Source: https://hub.docker.com/_/postgres/

## Run
```
docker run --rm \
  --name postgres13 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  postgres:13
```

## Check connection with telnet
```
export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres13)
telnet $POSTGRES_IP 5432
```

## Connect with PSQL
### From another container
```
docker run -it --rm \
  --link postgres13:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:13 \
  psql -h postgres -U pguser
```
### From host OS
Install PSQL: `sudo apt install -y postgresql-client-12`
Connect:
```
export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres13)
export PGPASSWORD=pgpass
psql -h $POSTGRES_IP -p 5432 -U pguser -d postgres
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
Container IP: `export POSTGRES_IP=$(docker inspect --format='{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres13)`
URL: `jdbc:postgresql://${POSTGRES_IP}:5432/postgres`
Login: `pguser`
Password: `pgpass`
