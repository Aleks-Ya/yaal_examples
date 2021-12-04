# Hadoop HDFS cluster with Kerberos (no HA)

## Run cluster

1. Build: `./build.sh` 
2. Start cluster: `./run_cluster.sh`
3. Update hosts file: `sudo ./update_hosts.sh`
4. Check: http://hdfs-master.hdfs.yaal.ru:50070
5. Check logs:
    1. DataNode 1: `docker exec hdfs-master cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-master.log`
    2. DataNode 2: `docker exec hdfs-slave1 cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-slave1.log`
    3. DataNode 3: `docker exec hdfs-slave2 cat /opt/hadoop/logs/hadoop-hdfs-datanode-hdfs-slave2.log`

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
    1. Enable Kerberos: Firefox -> `about:config` -> `network.negotiate-auth.trusted-uris` = `.hdfs.yaal.ru`
       (or `hdfs-master.hdfs.yaal.ru,hdfs-slave1.hdfs.yaal.ru,hdfs-slave2.hdfs.yaal.ru`)
    2. Open http://hdfs-master.hdfs.yaal.ru:50070
5. Chrome to Web Console:
   `google-chrome --auth-server-whitelist="*.yaal.ru" --auth-negotiate-delegate-whitelist="*.yaal.ru"`

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it hdfs-sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:
   `docker exec -it hdfs-sclient bash -c "kinit -kt \$KEYTAB sclient@HADOOPCLUSTER.LOCAL && sclient hdfs-sserver.hdfs.yaal.ru 5968 sserver"`