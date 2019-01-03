# Hadoop YARN on single node

## TODO
1. Rename "Hadoop Cluster" to "HDFS HA Cluster"
1. Remove YARN and MapReduce (including two run_application.sh)
1. Setup active failover
1. Publish to Docker Hub
1. Give different names for services and hostnames in docker-compose.yml
1. Set default replication factor to 2
1. Remove SSH to localhost and 0.0.0.0

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html#YARN_on_a_Single_Node

## Build image
`./build.sh`

## Run image
Run YARN cluster:

`./run_cluster.sh`

Run standard MapReduce example (after the cluster has started):

`./run_application.sh`

Run HDFS command:

`docker exec -it hadoop-yarn-single ./bin/hdfs dfs -ls /`

## Stop cluster
`docker stop hadoop-yarn-single`

## UI
- Active Name Node UI: http://localhost:50070
- Standby Name Node UI: http://localhost:51070
- Data Node 1 UI: http://localhost:50075
- Data Node 2 UI: http://localhost:51075
- Data Node 3 UI: http://localhost:52075
