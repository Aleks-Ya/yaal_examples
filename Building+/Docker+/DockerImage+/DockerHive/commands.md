# Hive Docker Image

## Info
### Sources
[DockerHub](https://hub.docker.com/r/mcapitanio/hive)

[GitHub](https://github.com/parrot-stream/docker-hive)

### Versions

Available versions: 1.2.1, 1.2.2, 2.1.0, 2.1.1

All versions: https://hub.docker.com/r/mcapitanio/hive/tags/

## Run

```
git clone git@github.com:parrot-stream/docker-hive.git
cd docker-hive
git checkout 1.2.2
docker-compose -p docker up
```

## URLs
* Hadoop
  * NameNode:			http://localhost:50070
  * DataNode:			http://localhost:50075
  * YARN Node Manager:		http://localhost:8042
  * YARN Resource Manager:		http://localhost:8088
  * YARN Application History:	http://localhost:8188
  * MapReduce Job History:		http://localhost:19888/jobhistory

## Run Hive CLI
```
# Quick
docker exec -it docker_hive_1 hive

# Using bash
docker exec -it docker_hive_1 bash
$hive
```

## Stop
`docker-compose -p docker down --remove-orphans`

## Logs
HCAT: /opt/hive/logs/
Hadoop (YARN, NameNode, DataNode): /opt/hadoop/logs
HiveServer2:
  /tmp/root/hive.log
  /tmp/hiveserver2-stderr---supervisor-dzHd_o.log
  /tmp/hiveserver2-stdout---supervisor-Xv6xD9.log
Supervisor: /tmp/supervisord.log
