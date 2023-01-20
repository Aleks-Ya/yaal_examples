# docker-info CLI

## All fields
Print all available fields: `docker info -f '{{json .}}' | python -m json.tool`

## Specific fields
Local repository location: `docker info -f '{{.DockerRootDir}}'`
Number of CPUs: `docker info -f '{{.NCPU}}'`
Size of memory: `docker info -f '{{.MemTotal}}' | numfmt --to=iec`
