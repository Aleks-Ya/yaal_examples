#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"
echo "HADOOP_LOG_DIR=$HADOOP_LOG_DIR"

echo "Starting DataNode..."
su hdfs -c "hadoop-daemon.sh --script hdfs start datanode"
echo "DataNode started."

echo "Starting NodeManager..."
su yarn -c "yarn-daemon.sh --config $HADOOP_CONF_DIR start nodemanager"
echo "NodeManager started."

SIGINT=2
SIGTERM=15
stop()
{
  su yarn -c "yarn-daemon.sh --config $HADOOP_CONF_DIR stop nodemanager"
  su hdfs -c "hadoop-daemon.sh --script hdfs stop datanode"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
