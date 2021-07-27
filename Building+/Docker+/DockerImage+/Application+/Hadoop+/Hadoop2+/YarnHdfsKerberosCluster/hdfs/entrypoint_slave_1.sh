#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

export KERBEROS_SHARED=/tmp/kerberos
ls -l "$KERBEROS_SHARED"
cp "$KERBEROS_SHARED/dn2.keytab" /etc/hdfs.keytab
kinit -kt /etc/hdfs.keytab dn/yarn-slave1.yarn.yaal.ru@HADOOPCLUSTER.LOCAL

echo "Starting DataNode..."
hadoop-daemon.sh --script hdfs start datanode
echo "DataNode started."

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
