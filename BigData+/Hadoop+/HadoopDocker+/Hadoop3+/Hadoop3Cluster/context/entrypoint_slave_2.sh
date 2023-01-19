#!/bin/bash

set -e

echo "HADOOP_HOME=$HADOOP_HOME"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

echo "Starting JournalNode..."
hdfs --daemon start journalnode
echo "JournalNode started."

echo "Starting DataNode..."
hdfs --daemon start datanode
echo "DataNode started."

echo "Starting NodeManager..."
yarn nodemanager
echo "NodeManager started."

SIGINT=2
SIGTERM=15
stop()
{
  hdfs --daemon stop datanode
  hdfs --daemon stop journalnode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
