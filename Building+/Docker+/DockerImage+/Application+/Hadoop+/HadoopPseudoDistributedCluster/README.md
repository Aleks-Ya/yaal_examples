# Hadoop Pseudo-Distributed Cluster

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html#Pseudo-Distributed_Operation

## Build image
`docker build -t hadoop-pseudo-distributed-cluster .`

## Run image
Run standard MapReduce example:

`docker run --rm --name hadoop-pseudo-distributed hadoop-pseudo-distributed-cluster`

Do HDFS command:

`docker run --rm --name hadoop-single-node hadoop-pseudo-distributed-cluster ./bin/hdfs dfs -ls /`

## Run command in container
`docker run --rm --name hadoop-single-node hadoop-pseudo-distributed-cluster java --version`

## Run Bash
`docker run --rm -it --name hadoop-single-node hadoop-pseudo-distributed-cluster bash`
