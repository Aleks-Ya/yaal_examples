# avro CLI

Install: `pip install avro`

Help: `avro -h`
Help about a command: `avro write -h`

## Write
Write an Avro file: `cat ./write_read/city.ndjson | avro write -s ./write_read/city.avsc -f json -o ./write_read/city.avro`

## Read
Print schema of an Avro file: `avro cat -p ./write_read/city.avro`
Read all records: `avro cat ./write_read/city.avro`
Read N records: `avro cat -n 1 ./write_read/city.avro`
Read as CSV format: `avro cat -f csv ./write_read/city.avro`
Read as pretty JSON: `avro cat -f json-pretty ./write_read/city.avro`

## Schema
Bytes field: 
1. Write: `cat ./field/bytes/bytes.ndjson | avro write -s ./field/bytes/bytes.avsc -f json -o ./field/bytes/bytes.avro`

## All Data Types
Exception: `bytes`, `fixed` and `union` do not work.
1. Write: `cat ./field/all_types/all_types.ndjson | avro write -s ./field/all_types/all_types.avsc -f json -o ./field/all_types/all_types.avro`
2. Read: `avro cat -f json-pretty ./field/all_types/all_types.avro`
3. Print schema: `avro cat -p ./field/all_types/all_types.avro`
