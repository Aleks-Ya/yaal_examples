# Hadoop Single Node Cluster

## Documentation
https://hadoop.apache.org/docs/r3.2.2/hadoop-project-dist/hadoop-common/SingleCluster.html

## Build image
Hadoop version: 3.3.3 (version list: http://apache-mirror.rbc.ru/pub/apache/hadoop/common/)

Image version: 1.0 (tag for created Docker image)

`./build.sh 3.3.3 1.0`

Run Bash for testing: `docker run -it --rm hadoop-base-image-3.3.3:1.0 bash`
