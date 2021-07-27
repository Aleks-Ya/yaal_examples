# Hadoop cluster (HDFS, YARN, Spark)

## TODO
1. Setup active failover (https://hadoop.apache.org/docs/r2.10.1/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html)
1. Add Docker HEALTHCHECK
1. Publish to Docker Hub
1. Setup security (Kerberos, encryption)
1. Fix JournalNode start

## Build images
Choose Hadoop and Spark versions in `.env` file.
`./build.sh`

## Run cluster
```
./run_cluster.sh
sudo ./update_hosts.sh
```

## CLI

### Run example apps
YARN: `docker exec -it hadoop-cluster-master su hdfs bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`
Spark: `docker exec -it hadoop-cluster-master su hdfs bash -c '${HADOOP_PREFIX}/run_spark_example.sh'`

### Hadoop CLI
Run Bash with Hadoop CLI available: `./run_cli.sh`

Examples of commands:
- See HDFS Data Node statuses: `hdfs dfsadmin -report`
- See HDFS Name Node statuses: `hdfs haadmin -getAllServiceState`
- See YARN nodes: `yarn node -list -all`
- Run example YARN application: `su hdfs bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`
- Run example Spark application: `su hdfs bash -c '${HADOOP_PREFIX}/run_spark_example.sh'`

### ZooKeeper CLI
Run ZooKeeper CLI: `./run_zk_cli.sh`

## Remove cluster
`docker-compose down`

## Web UI
- HDFS
  - Active Name Node UI: http://master-service:50070
  - Standby Name Node UI: http://slave-service-1:50070
  - Data Node 1 UI: http://master-service:50075
  - Data Node 2 UI: http://slave-service-1:50075
  - Data Node 3 UI: http://slave-service-2:50075
- YARN
  - Resource Manager Web UI: http://master-service:8088
  - Node Manager 1 Web UI: http://master-service:8042
  - Node Manager 2 Web UI: http://slave-service-1:8042
  - Node Manager 3 Web UI: http://slave-service-2:8042
- Spark
  - History Server Web UI: http://master-service:18080
