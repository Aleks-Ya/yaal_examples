# Hadoop Single Node Cluster

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html

## Build image
`docker build -t hadoop-single-node-cluster .`

## Run image
Run standard MapReduce example:

`docker run --rm --name hadoop-single-node hadoop-single-node-cluster`

Execute HDFS commands:

`docker run --rm --name hadoop-single-node hadoop-single-node-cluster ./bin/hdfs dfs -ls /`

Run custom command in container:

`docker run --rm --name hadoop-single-node hadoop-single-node-cluster java --version`
