#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"
echo "HADOOP_LOG_DIR=$HADOOP_LOG_DIR"

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
export YARN_LOG_DIR=$HADOOP_LOG_DIR
su yarn -c "yarn-daemon.sh --config $HADOOP_CONF_DIR start resourcemanager"
echo "ResourceManager started."

SIGINT=2
SIGTERM=15
stop()
{
  su yarn -c "yarn-daemon.sh --config $HADOOP_CONF_DIR stop resourcemanager"
  hadoop-daemon.sh --script hdfs stop namenode
  hadoop-daemon.sh --script hdfs stop datanode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
