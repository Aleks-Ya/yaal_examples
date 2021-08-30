# Hadoop HDFS cluster with Kerberos (no HA)

## Build images

`./build.sh`

## Run cluster

1. Start cluster: `./run_cluster.sh`
2. Update hosts file: `sudo ./update_hosts.sh`
3. Check HDFS: http://yarn-master.yarn.yaal.ru:50070
4. Check YARN: http://yarn-master.yarn.yaal.ru:8088
5. Check logs (HDFS):
  1. DataNode 1: `docker exec yarn-master cat /opt/hadoop/logs/hadoop-hdfs-datanode-yarn-master.log`
  1. DataNode 2: `docker exec yarn-slave1 cat /opt/hadoop/logs/hadoop-hdfs-datanode-yarn-slave1.log`
  1. DataNode 3: `docker exec yarn-slave2 cat /opt/hadoop/logs/hadoop-hdfs-datanode-yarn-slave2.log`
6. Check logs (YARN):
  1. ResourceManager: `docker exec yarn-master bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-resourcemanager-yarn-master.log'`
  1. NodeManager 1: `docker exec yarn-slave1 bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-nodemanager-yarn-slave1.log'`
  1. NodeManager 2: `docker exec yarn-slave2 bash -c 'cat $HADOOP_LOG_DIR/yarn-yarn-nodemanager-yarn-slave2.log'`

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it yarn-master bash`  
Example of a command: `hdfs dfs -ls /`

### Run example apps
YARN: `docker exec -it yarn-master bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`

## Stop cluster

`docker-compose stop`

## Web UI

- HDFS
    - Active Name Node UI: http://yarn-master.yarn.yaal.ru:50070
    - Data Node 1 UI: http://yarn-master.yarn.yaal.ru:1006
    - Data Node 2 UI: http://yarn-slave1.yarn.yaal.ru:1006
    - Data Node 3 UI: http://yarn-slave2.yarn.yaal.ru:1006
- YARN
    - Resource Manager Web UI: http://yarn-master.yarn.yaal.ru:8088
    - Node Manager 1 Web UI: http://yarn-slave1.yarn.yaal.ru:8042
    - Node Manager 2 Web UI: http://yarn-slave2.yarn.yaal.ru:8042

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it yarn-sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:
   `docker exec -it yarn-sclient bash -c "kinit -kt \$KEYTAB sclient@HADOOPCLUSTER.LOCAL && sclient yarn-sserver.yarn.yaal.ru 5968 sserver"`