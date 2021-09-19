#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

echo "Starting HDFS..."
echo "Formatting namenode..."
hdfs namenode -format -nonInteractive
echo "Namenode is formatted"

echo "Starting namenode..."
hadoop-daemon.sh --script hdfs start namenode
echo "Namenode started"
echo "Starting datanode..."
hadoop-daemon.sh --script hdfs start datanode
echo "HDFS started."

SIGINT=2
SIGTERM=15
stop()
{
  hadoop-daemon.sh --script hdfs stop namenode
  hadoop-daemon.sh --script hdfs stop datanode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
