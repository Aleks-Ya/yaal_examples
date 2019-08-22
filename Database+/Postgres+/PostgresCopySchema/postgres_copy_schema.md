# Copy Postgres schema to another one (in the same instance)

## 1. Run Postgres
```
docker run --rm \
  --name postgres11 \
  -e POSTGRES_USER=pguser \
  -e POSTGRES_PASSWORD=pgpass \
  -d postgres:11
```

## 2. Create source schema
### Connect via PSQL
```
docker run -it --rm \
  --link postgres11:postgres \
  --env PGPASSWORD="pgpass" \
  postgres:11 \
  psql -h postgres -U pguser
```

### Create source data
In PSQL:
```
DROP SCHEMA IF EXISTS srs CASCADE;
DROP SCHEMA IF EXISTS dest CASCADE;
CREATE SCHEMA src;
CREATE TABLE src.data (id int PRIMARY KEY);
INSERT INTO src.data VALUES(1), (2);
```

## 3. Copy `src` schema to `dest`
### Run pg_dump
```
docker cp copy_schema.sh postgres11:/tmp/copy_schema.sh \
&& docker exec -it \
    --env FROM="src" \
    --env TO="dest" \
    --env DB="pguser" \
    --env USER="pguser" \
    --env PGPASSWORD="pgpass" \
    postgres11 \
  /tmp/copy_schema.sh
```

## 4. Check new schema
In PSQL:
```
SELECT * FROM dest.data;
```

## 5. Stop Postgres
`docker stop postgres11`