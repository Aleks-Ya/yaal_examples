# hdfs CLI

Location: `$HADOOP_PREFIX/bin/hdfs`

## haadmin
Show state of a Name Node: `hdfs haadmin -getServiceState nn2`
Show state of all Name Nodes: `hdfs haadmin -getAllServiceState`
Make a Name Node active: `hdfs haadmin -transitionToActive --forceactive nn1`
Make a Name Node standby: `hdfs haadmin -transitionToStandby nn1`
Check health of Name Node (OK if no output): `hdfs haadmin -checkHealth nn1`

## dfsadmin
Status of whole cluster: `hdfs dfsadmin -report`

## dfs
Help about a command: `hdfs dfs -help setrep`
List files in the root folder: `hdfs dfs -ls /`
List all files in HDFS: `hdfs dfs -ls -R / > all.txt`
Put a file to HDFS (error if exists): `hdfs dfs -put all.txt /tmp`
Put a file to HDFS (overwrite if exists): `hdfs dfs -put -f all.txt /tmp`
Put a file from pipe: `echo "abc" | hdfs dfs -put - /data.txt`
Show content of a file in HDFS: `hdfs dfs -cat /tmp/all.txt`
Delete a directory recursively: `hdfs dfs -rm -r -f /data`
Create directories: `hdfs dfs -mkdir -p /big_data_201/expedia-hotel-recommendations`
Set replication factor for a file: `hdfs dfs -setrep 2 /big_data_201/destinations.csv`
Set replication factor for a folder: `hdfs dfs -setrep 2 /big_data_201/`
Read a sequence file: `hdfs dfs -text /tmp/sequence_file.seq`

## getconf
Show value of a configuration property: `hdfs getconf -confKey yarn.scheduler.maximum-allocation-mb`
