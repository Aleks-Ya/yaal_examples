#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

/usr/sbin/sshd
ssh -o StrictHostKeyChecking=no localhost cat /dev/null
ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null
ssh -o StrictHostKeyChecking=no master-service cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-1 cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-2 cat /dev/null

echo "Starting JournalNode..."
$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR start journalnode

echo "Formatting Name Node..."
$HADOOP_PREFIX/bin/hdfs namenode -format

echo "Starting Active NameNode..."
$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode

echo "Starting DataNode..."
$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start datanode

$HADOOP_PREFIX/bin/hdfs haadmin -transitionToActive --forceactive nn1

SIGINT=2
SIGTERM=15
stop()
{
  $HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs stop namenode
  $HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs stop datanode
  $HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script stop journalnode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
