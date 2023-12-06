# avro CLI

Install: `pip install avro`

Help: `avro -h`
Help about a command: `avro write -h`

## Write
Write an Avro file: `cat city.ndjson | avro write -s city.avsc -f json -o city.avro`

## Read
Print schema of an Avro  file: `avro cat -p city.avro`
Read all records: `avro cat city.avro`
Read N records: `avro cat -n 1 city.avro`
Read as CSV format: `avro cat -f csv  city.avro`
Read as pretty JSON: `avro cat -f json-pretty  city.avro`

## Schema
Bytes field: 
1. Write: `cat ./field/bytes/bytes.ndjson | avro write -s ./field/bytes/bytes.avsc -f json -o ./field/bytes/bytes.avro`
