# Hadoop YARN on single node

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html#YARN_on_a_Single_Node

## Build image
`./build.sh`

## Run image
Run YARN cluster:

`./run_cluster.sh`

Run standard MapReduce example (after the cluster has started):

`./run_client_application.sh`

Run HDFS command:

`docker exec -it hadoop-yarn-single ./bin/hdfs dfs -ls /`

## Stop cluster
`docker stop hadoop-yarn-single`

## UI
- Hadoop UI: http://localhost:8088
- HDFS Name node UI: http://localhost:50070
- HDFS Data node UI: http://localhost:50075
