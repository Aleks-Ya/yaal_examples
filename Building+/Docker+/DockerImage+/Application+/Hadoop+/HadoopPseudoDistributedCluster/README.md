# Hadoop Pseudo-Distributed Cluster

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html#Pseudo-Distributed_Operation

## Build image
`./build.sh`

## Run image
Run standard MapReduce example:

`./run.sh`

Do HDFS command:

`docker run --rm hadoop-pseudo-distributed-cluster ./bin/hdfs dfs -ls /`

## Run command in container
`./run.sh java -version`

## Run Bash
`docker run --rm -it hadoop-pseudo-distributed-cluster bash`
