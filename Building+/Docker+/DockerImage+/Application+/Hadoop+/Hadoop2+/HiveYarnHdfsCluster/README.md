# Hadoop cluster (Hive, HDFS, YARN)

## TODO

1. Setup active
   failover (https://hadoop.apache.org/docs/r2.10.1/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html)
1. Add Docker HEALTHCHECK
1. Publish to Docker Hub
1. Setup security (Kerberos, encryption)
1. Fix JournalNode start

## Run cluster
1. Build: `./build.sh`
1. Run: `./run_cluster.sh`
1. Update hosts: `sudo ./update_hosts.sh` 

## Log

Hive logs: `docker exec hive-yarn-hdfs-master cat /opt/hive/logs/hive.log`

## CLI

### Run example apps

YARN: `docker exec -it hive-yarn-hdfs-master su hdfs bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`

### Hadoop CLI

Run Bash with Hadoop CLI available: `./run_cli.sh`

Examples of commands:

- See HDFS Data Node statuses: `hdfs dfsadmin -report`
- See HDFS Name Node statuses: `hdfs haadmin -getAllServiceState`
- See YARN nodes: `yarn node -list -all`
- Run example YARN application: `su hdfs bash -c '${HADOOP_PREFIX}/run_yarn_example.sh'`

### Hive CLI
Beeline : `docker exec -it hive-yarn-hdfs-master beeline -u jdbc:hive2://localhost:10000 -n '' -p ''`
Hive CLI (deprecated): `docker exec -it hive-yarn-hdfs-master hive`
Example of commands: `show databases;`

### Hive JDBC
Outside of Docker
URL: `jdbc:hive2://master-service:10000`
Database/schema: empty
Login: empty
Password: empty

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
- Hive
    - HiveServer2 Web: http://master-service:10002 
