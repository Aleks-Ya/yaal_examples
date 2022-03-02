# pg_dump CLI

Dump table
Only schema: pg_dump -h 10.3.50.227 -p 6433 -U ahml -t 'dpm.indicator_catalog' --schema-only ahml
Schema and data (with COPY): pg_dump -h 10.3.50.227 -p 6433 -U ahml -t 'dpm.indicator_catalog' -f output.dmp
Schema and data (with INSERT): pg_dump -h 10.3.50.227 -p 6433 -U ahml --column-inserts -t 'dpm.indicator_catalog' -f output.dmp
Schema and data (with INSERT, no GRANT): pg_dump -h 10.3.50.227 -p 6433 -U ahml --column-inserts --no-owner -t 'dpm.indicator_catalog' -f output.dmp

Dump schema
pg_dump -h 10.3.50.227 -p 6433 -U ahml --schema=cbrf -f output.dmp

Copy schema to another one
export FROM="fromschema" && export TO="toschema" && pg_dump -U user --schema="$FROM' database | sed "s/ \${FROM}\./ ${TO}./g" | psql -U user -d database