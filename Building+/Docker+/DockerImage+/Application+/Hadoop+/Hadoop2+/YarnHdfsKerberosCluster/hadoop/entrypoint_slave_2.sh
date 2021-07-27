#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

export KERBEROS_SHARED=/tmp/kerberos
ls -l "$KERBEROS_SHARED"
cp "$KERBEROS_SHARED/dn3.keytab" /etc/hdfs.keytab
cp "$KERBEROS_SHARED/krb5.conf" /etc/krb5.conf
kinit -kt /etc/hdfs.keytab dn/yarn-slave2.yarn.yaal.ru@HADOOPCLUSTER.LOCAL

echo "Starting DataNode..."
hadoop-daemon.sh --script hdfs start datanode
echo "DataNode started."

echo "Starting NodeManager..."
kinit -kt /etc/hdfs.keytab nm/yarn-slave2.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
hadoop-daemon.sh --script yarn start nodemanager
echo "NodeManager started."

SIGINT=2
SIGTERM=15
stop()
{
  hadoop-daemon.sh --script yarn stop nodemanager
  hadoop-daemon.sh --script hdfs stop datanode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
