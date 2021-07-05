# Hadoop Docker image

## MiniCluster
Docs: https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/CLIMiniCluster.html
Run: `mapred minicluster -rmport 555 -jhsport 556`

## Deprecated
Source: https://hub.docker.com/r/sequenceiq/hadoop-docker

### Run
`docker run -it sequenceiq/hadoop-docker:2.7.1 /etc/bootstrap.sh -bash`

### HDFS UI
`http://172.17.0.2:50070`

### Test
#### Map reduce
`bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.0.jar grep input output 'dfs[a-z.]+'`

#### HDFS
`bin/hdfs dfs -cat output/*`
