#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

export KRB5_CONFIG=/tmp/kerberos/krb5.conf
kinit -kt /tmp/kerberos/hdfs.keytab hdfs/hdfs-slave1.yaal.ru@HADOOPCLUSTER.LOCAL

echo "Starting HDFS..."
hadoop-daemon.sh --script hdfs start datanode
echo "HDFS started."

SIGINT=2
SIGTERM=15
stop()
{
  hadoop-daemon.sh --script hdfs stop datanode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
