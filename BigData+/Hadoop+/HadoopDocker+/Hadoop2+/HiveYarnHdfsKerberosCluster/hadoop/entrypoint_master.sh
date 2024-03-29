#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

export KERBEROS_SHARED=/tmp/kerberos
ls -l "$KERBEROS_SHARED"
cp "$KERBEROS_SHARED/nn.keytab" /etc/hdfs.keytab
cp "$KERBEROS_SHARED/krb5.conf" /etc/krb5.conf
klist -kte /etc/hdfs.keytab
echo "kinit..."
kinit -kt /etc/hdfs.keytab nn/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
kinit -kt /etc/hdfs.keytab dn/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
echo "kinit done"

echo "klist..."
klist
echo "klist done"

echo "Starting HDFS..."
echo "Formatting namenode..."
hdfs namenode -format -nonInteractive
echo "Namenode is formatted"

echo "Starting NameNode..."
hadoop-daemon.sh --script hdfs start namenode
echo "NameNode started"
echo "Starting DataNode..."
hadoop-daemon.sh --script hdfs start datanode
echo "DataNode started."

echo "Starting ResourceManager..."
kinit -kt /etc/hdfs.keytab rm/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
hadoop-daemon.sh --script yarn start resourcemanager
echo "ResourceManager started."

echo "Starting Hive..."
su hdfs -c "hdfs dfs -mkdir -p /user/hive/warehouse"
su hdfs -c "hdfs dfs -chown -R hive /user/hive/warehouse"
su hive -c "schematool -dbType derby -initSchema -verbose"
su hive -c "hiveserver2"
echo "Hive started."

SIGINT=2
SIGTERM=15
stop()
{
  stop-yarn.sh
  hadoop-daemon.sh --script hdfs stop namenode
  hadoop-daemon.sh --script hdfs stop datanode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
