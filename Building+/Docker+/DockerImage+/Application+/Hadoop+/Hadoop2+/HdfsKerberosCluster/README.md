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

## Web

### From container

1. Curl to Web Console
   `docker exec -it hdfs-master curl -i --negotiate -u :  http://hdfs-master.hdfs.yaal.ru:50070`
2. Curl to WebHDFS
   `docker exec -it hdfs-master curl -i --negotiate -u :  http://hdfs-master.hdfs.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS`

### Outside container

1. Authentication

```
docker cp hdfs-master:/tmp/kerberos/krb5.conf /tmp/krb5.conf
docker cp hdfs-master:/tmp/kerberos/nn.keytab /tmp/nn.keytab
export KRB5_CONFIG=/tmp/krb5.conf
kinit -kt /tmp/nn.keytab dn/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL
```

2. Curl to Web Console
   `curl -i --negotiate -u :  http://hdfs-master.hdfs.yaal.ru:50070`
3. Curl to WebHDFS
   `curl -i --negotiate -u : "http://hdfs-master.hdfs.yaal.ru:50070/webhdfs/v1/?op=LISTSTATUS"`
4. Firefox to Web Console  
   Open http://hdfs-master.hdfs.yaal.ru:50070

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it hdfs-sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:
   `docker exec -it hdfs-sclient bash -c "kinit -kt \$KEYTAB sclient@HADOOPCLUSTER.LOCAL && sclient hdfs-sserver.hdfs.yaal.ru 5968 sserver"`