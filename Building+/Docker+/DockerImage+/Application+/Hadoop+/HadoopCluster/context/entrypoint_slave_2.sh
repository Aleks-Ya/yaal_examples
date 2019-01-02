#!/bin/bash

set -e
/usr/sbin/sshd
ssh -o StrictHostKeyChecking=no localhost cat /dev/null
ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null
ssh -o StrictHostKeyChecking=no master-service cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-1 cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-2 cat /dev/null

echo "Starting JournalNode..."
$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR start journalnode

echo "Starting Standby NameNode..."
#$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode -bootstrapStandby
#$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode -initializeSharedEdits

echo "Starting DataNode..."
$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start datanode

SIGINT=2
SIGTERM=15
stop()
{
  $HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs stop datanode
  #$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs stop namenode
  $HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script stop journalnode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
