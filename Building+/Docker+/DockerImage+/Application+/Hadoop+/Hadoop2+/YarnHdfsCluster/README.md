# Hadoop YARN cluster (no HA)

Components: HDFS, YARN.

## Build images

`./build.sh`

## Run cluster

1. Start cluster: `./run_cluster.sh`
2. Update hosts file: `sudo ./update_hosts.sh`
3. Check HDFS: http://yarn-hdfs-master.yarn.yaal.ru:50070
4. Check YARN: http://yarn-hdfs-master.yarn.yaal.ru:8088
5. Check logs (HDFS):
  1. DataNode 1: `docker exec yarn-hdfs-master bash -c 'cat $HADOOP_LOG_DIR/hadoop--namenode-yarn-hdfs-master.log'`
  1. DataNode 2: `docker exec yarn-hdfs-slave1 bash -c 'cat $HADOOP_LOG_DIR/hadoop--datanode-yarn-hdfs-slave1.log'`
  1. DataNode 3: `docker exec yarn-hdfs-slave2 bash -c 'cat $HADOOP_LOG_DIR/hadoop--datanode-yarn-hdfs-slave2.log'`
6. Check logs (YARN):
  1. ResourceManager: `docker exec yarn-hdfs-master bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-resourcemanager-yarn-hdfs-master.log'`
  1. NodeManager 1:   `docker exec yarn-hdfs-slave1 bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-nodemanager-yarn-hdfs-slave1.log'`
  1. NodeManager 2:   `docker exec yarn-hdfs-slave2 bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-nodemanager-yarn-hdfs-slave2.log'`

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it yarn-hdfs-master bash`  
Example of a command: `hdfs dfs -ls /`

### Run example apps

YARN: `docker exec -it yarn-hdfs-master bash -c '$HADOOP_PREFIX/run_yarn_example.sh'`

## Stop cluster

`docker-compose stop`

## Web UI

- HDFS
    - Active Name Node UI: http://yarn-hdfs-master.yarn.yaal.ru:50070
    - Data Node 1 UI: http://yarn-hdfs-master.yarn.yaal.ru:1006
    - Data Node 2 UI: http://yarn-hdfs-slave1.yarn.yaal.ru:1006
    - Data Node 3 UI: http://yarn-hdfs-slave2.yarn.yaal.ru:1006
- YARN
    - Resource Manager Web UI: http://yarn-hdfs-master.yarn.yaal.ru:8088
    - Node Manager 1 Web UI: http://yarn-hdfs-slave1.yarn.yaal.ru:8042
    - Node Manager 2 Web UI: http://yarn-hdfs-slave2.yarn.yaal.ru:8042
