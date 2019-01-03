# Hadoop HDFS High Available (with QJM) cluster

## TODO
1. Setup active failover (https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html)
1. Publish to Docker Hub

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html#YARN_on_a_Single_Node

## Build images
`./build.sh`

## Run cluster
`./run_cluster.sh`

## Hadoop CLI
Run Bash with Hadoop CLI available: `./run_cli.sh`

Example of a command: `hdfs dfsadmin -report`

## Stop cluster
`docker-compose stop`

## UI
- Active Name Node UI: http://localhost:50070
- Standby Name Node UI: http://localhost:51070
- Data Node 1 UI: http://localhost:50075
- Data Node 2 UI: http://localhost:51075
- Data Node 3 UI: http://localhost:52075
