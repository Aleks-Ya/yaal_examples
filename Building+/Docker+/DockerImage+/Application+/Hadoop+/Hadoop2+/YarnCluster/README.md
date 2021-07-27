# Hadoop YARN cluster (without HDFS!)

## Build images
`./build.sh`

## Run cluster
`./run_cluster.sh`

## Hadoop CLI
Run Bash with Hadoop CLI available: `./run_cli.sh`

Example of a command: `yarn application -list`

## Stop cluster
`docker-compose stop`

## Web UI
- Resource Manager Web UI: http://localhost:8088
- Node Manager 1 Web UI: http://localhost:8142
- Node Manager 2 Web UI: http://localhost:8242
