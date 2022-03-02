# minicluster Hadoop CLI

Docs

[Hadoop: CLI MiniCluster](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/CLIMiniCluster.html)

CLI


Cluster

Show help:
```
./bin/mapred minicluster -help
```
1st run cluster:
```
./bin/mapred minicluster -format
```
2nd run cluster:
```
./bin/mapred minicluster -writeConfig /tmp/hadoop-mini-cluster-config.xml
```

HDFS client

All commands: HDFS CLI
Cluster status:
```
./bin/hdfs dfsadmin -conf /tmp/hadoop-mini-cluster-config.xml -report
```
List files in HDFS:
```
./bin/hdfs dfs -conf /tmp/hadoop-mini-cluster-config.xml -ls /
```

Links

ResourceManager Web Interface: 
- Home: http://localhost:8088
- Log level: http://localhost:8088/logLevel
NameNode: http://localhost:40971

Errors


"storage directory does not exist or is not accessible"

Message:
```
org.apache.hadoop.hdfs.server.common.InconsistentFSStateException: Directory /home/aleks/installed/Hadoop3/target/test/data/dfs/name-0-1 is in an inconsistent state: storage directory does not exist or is not accessible.
```
Fix: format DFS on 1st run
```
./bin/mapred minicluster -format
```
