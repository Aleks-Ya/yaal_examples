# Spark Cluster on Standalone Spark instances

## Build
`./build.sh`

## Start cluster
1. Start: `docker-compose up`
2. Publis hosts: `sudo ./update_hosts.sh`
3. Open: http://spark-standalone-cluster-master:8080

## Stop cluster
`docker-compose stop`

## Delete cluster
`docker-compose down`
