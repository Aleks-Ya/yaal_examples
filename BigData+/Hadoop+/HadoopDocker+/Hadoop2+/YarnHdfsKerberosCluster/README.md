# Hadoop HDFS cluster with Kerberos (no HA)

## Run cluster

1. Build: `./build.sh`
1. Start cluster: `./run_cluster.sh`
1. Update hosts file: `sudo ./update_hosts.sh`
1. Check HDFS: http://yarn-hdfs-kerberos-master.yarn.yaal.ru:50070
1. Check YARN: http://yarn-hdfs-kerberos-master.yarn.yaal.ru:8088
1. Check logs (HDFS):
  1. NameNode:   `docker exec yarn-hdfs-kerberos-master bash -c 'cat $HADOOP_LOG_DIR/hadoop--namenode-yarn-hdfs-kerberos-master.log'` 
  1. DataNode 1: `docker exec yarn-hdfs-kerberos-master bash -c 'cat $HADOOP_LOG_DIR/hadoop-hdfs-datanode-yarn-hdfs-kerberos-master.log'`
  1. DataNode 2: `docker exec yarn-hdfs-kerberos-slave1 bash -c 'cat $HADOOP_LOG_DIR/hadoop-hdfs-datanode-yarn-hdfs-kerberos-slave1.log'`
  1. DataNode 3: `docker exec yarn-hdfs-kerberos-slave2 bash -c 'cat $HADOOP_LOG_DIR/hadoop-hdfs-datanode-yarn-hdfs-kerberos-slave2.log'`
1. Check logs (YARN):
  1. ResourceManager: `docker exec yarn-hdfs-kerberos-master bash -c 'cat $YARN_LOG_DIR/yarn-yarn-resourcemanager-yarn-hdfs-kerberos-master.log'`
  1. NodeManager 1:   `docker exec yarn-hdfs-kerberos-slave1 bash -c 'cat $YARN_LOG_DIR/yarn-yarn-nodemanager-yarn-hdfs-kerberos-slave1.log'`
  1. NodeManager 2:   `docker exec yarn-hdfs-kerberos-slave2 bash -c 'cat $YARN_LOG_DIR/yarn-yarn-nodemanager-yarn-hdfs-kerberos-slave2.log'`

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it yarn-hdfs-kerberos-master bash`  
Example of a command: `hdfs dfs -ls /`

### Run example apps
YARN: `docker exec -it yarn-hdfs-kerberos-master bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`

## Stop cluster

`docker-compose stop`

## Web UI

- HDFS
    - Active Name Node UI: http://yarn-hdfs-kerberos-master.yarn.yaal.ru:50070
    - Data Node 1 UI: http://yarn-hdfs-kerberos-master.yarn.yaal.ru:1006
    - Data Node 2 UI: http://yarn-hdfs-kerberos-slave1.yarn.yaal.ru:1006
    - Data Node 3 UI: http://yarn-hdfs-kerberos-slave2.yarn.yaal.ru:1006
- YARN
    - Resource Manager Web UI: http://yarn-hdfs-kerberos-master.yarn.yaal.ru:8088
    - Node Manager 1 Web UI: http://yarn-hdfs-kerberos-slave1.yarn.yaal.ru:8042
    - Node Manager 2 Web UI: http://yarn-hdfs-kerberos-slave2.yarn.yaal.ru:8042

## Web

### From container

1. Curl to Web Console
   `docker exec -it yarn-hdfs-kerberos-master curl -i --negotiate -u :  http://hdfs-master.hdfs.yaal.ru:50070`
2. Curl to WebHDFS
   `docker exec -it yarn-hdfs-kerberos-master curl -i --negotiate -u :  http://hdfs-master.hdfs.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS`

### Outside container

1. Authentication

```
docker cp yarn-hdfs-kerberos-master:/tmp/kerberos/krb5.conf /tmp/krb5.conf
docker cp yarn-hdfs-kerberos-master:/tmp/kerberos/nn.keytab /tmp/nn.keytab
export KRB5_CONFIG=/tmp/krb5.conf
kinit -kt /tmp/nn.keytab dn/yarn-hdfs-kerberos-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
```

2. Curl to Web Console
   `curl -i --negotiate -u :  http://yarn-hdfs-kerberos-master.yarn.yaal.ru:50070`
3. Curl to WebHDFS
   `curl -i --negotiate -u : "http://yarn-hdfs-kerberos-master.yarn.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS"`
4. Firefox to Web Console
  1. Enable Kerberos: Firefox -> `about:config` -> `network.negotiate-auth.trusted-uris` = `.hdfs.yaal.ru`
     (or `hdfs-master.hdfs.yaal.ru,hdfs-slave1.hdfs.yaal.ru,hdfs-slave2.hdfs.yaal.ru`)
  1. Open http://yarn-hdfs-kerberos-master.yarn.yaal.ru:50070

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it yarn-hdfs-kerberos-sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:
   `docker exec -it yarn-hdfs-kerberos-sclient bash -c "kinit -kt \$KEYTAB sclient@HADOOPCLUSTER.LOCAL && sclient yarn-hdfs-kerberos-sserver.yarn.yaal.ru 5968 sserver"`