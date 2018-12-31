# Hadoop Single Node Cluster

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html

## Build image
`./build.sh`

## Run image
Run standard MapReduce example:

`./run.sh`

Execute HDFS commands:

`./run.sh ./bin/hdfs dfs -ls /`

Run custom command in container:

`./run.sh java -version`
