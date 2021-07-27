# Hadoop HDFS cluster with Kerberos (no HA)

## Build images

`./build.sh`

## Run cluster

1. Start cluster: `./run_cluster.sh`
2. Update hosts file: `sudo ./update_hosts.sh`
3. Check: http://hdfs-master.hdfs.yaal.ru:50070

## Hadoop CLI

Run Bash with Hadoop CLI available: `docker exec -it hdfs-master bash`  
Example of a command: `hdfs dfs -ls /`

## Stop cluster

`docker-compose stop`

## Web UI

- HDFS
    - Active Name Node UI: http://hdfs-master.hdfs.yaal.ru:50070
    - Data Node 1 UI: http://hdfs-master.hdfs.yaal.ru:1006
    - Data Node 2 UI: http://hdfs-slave1.hdfs.yaal.ru:1006
    - Data Node 3 UI: http://hdfs-slave2.hdfs.yaal.ru:1006

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it hdfs-sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:
   `docker exec -it hdfs-sclient bash -c "kinit -kt \$KEYTAB sclient@HADOOPCLUSTER.LOCAL && sclient hdfs-sserver.hdfs.yaal.ru 5968 sserver"`