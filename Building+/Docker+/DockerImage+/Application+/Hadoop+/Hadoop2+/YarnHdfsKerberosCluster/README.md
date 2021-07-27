# Hadoop HDFS cluster with Kerberos (no HA)

## Build images

`./build.sh`

## Run cluster

1. Start cluster: `./run_cluster.sh`
2. Update hosts file: `sudo ./update_hosts.sh`
3. Check HDFS: http://yarn-master.yarn.yaal.ru:50070
3. Check YARN: http://yarn-master.yarn.yaal.ru:8088

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it yarn-master bash`  
Example of a command: `hdfs dfs -ls /`

### Run example apps
YARN: `docker exec -it yarn-master ${HADOOP_PREFIX}/run_yarn_example.sh`

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