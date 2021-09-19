# Hadoop HDFS cluster (no HA, no Kerberos)

## Run cluster

1. Build: `./build.sh`
1. Start cluster: `./run_cluster.sh`
1. Update hosts file: `sudo ./update_hosts.sh`
1. Check: http://hdfs-master.hdfs.yaal.ru:50070
1. Check logs:
    1. DataNode 1: `docker exec hdfs-master cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-master.log`
    1. DataNode 2: `docker exec hdfs-slave1 cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-slave1.log`
    1. DataNode 3: `docker exec hdfs-slave2 cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-slave2.log`

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it hdfs-master bash`  
Example of a command: `hdfs dfs -ls /`

## Stop cluster

`docker-compose stop`

## Web UI

- HDFS
    - Active NameNode UI: http://hdfs-master.hdfs.yaal.ru:50070
    - DataNode 1 UI: http://hdfs-master.hdfs.yaal.ru:1006
    - DataNode 2 UI: http://hdfs-slave1.hdfs.yaal.ru:1006
    - DataNode 3 UI: http://hdfs-slave2.hdfs.yaal.ru:1006

## Web

### From container

1. Curl to Web Console: `docker exec -it hdfs-master curl -i http://hdfs-master.hdfs.yaal.ru:50070`
1. Curl to WebHDFS:
   `docker exec -it hdfs-master curl -i http://hdfs-master.hdfs.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS`

### Outside container

1. Curl to Web Console: `curl -i http://hdfs-master.hdfs.yaal.ru:50070`
1. Curl to WebHDFS: `curl -i "http://hdfs-master.hdfs.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS"`
1. Firefox to Web Console: http://hdfs-master.hdfs.yaal.ru:50070
