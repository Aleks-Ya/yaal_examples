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
DROP SCHEMA IF EXISTS schema1 CASCADE;
DROP SCHEMA IF EXISTS schema2 CASCADE;
CREATE SCHEMA schema1;
CREATE SCHEMA schema2;
CREATE TABLE schema1.src (id int PRIMARY KEY);
INSERT INTO schema1.src VALUES(1), (2);
```

## 3. Copy `src` schema to `dest`
### Run pg_dump
```
docker cp copy_table.sh postgres11:/tmp/copy_table.sh \
&& docker exec -it \
    --env DB="pguser" \
    --env SCHEMA_FROM="schema1" \
    --env SCHEMA_TO="schema2" \
    --env TABLE_FROM="src" \
    --env TABLE_TO="dest" \
    --env USER="pguser" \
    --env PGPASSWORD="pgpass" \
    postgres11 \
  /tmp/copy_table.sh
```

## 4. Check new schema
In PSQL:
```
SELECT * FROM schema2.dest;
```

## 5. Stop Postgres
`docker stop postgres11`