# Hadoop Single Node Cluster

## Documentation
https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-common/SingleCluster.html

## Build image
`docker build -t hadoop-single-node-cluster:1 .`

## Run image
`docker run --rm --name hadoop-single-node hadoop-single-node-cluster:1`

## Run command in container
`docker run --rm --name hadoop-single-node hadoop-single-node-cluster:1 java --version`
