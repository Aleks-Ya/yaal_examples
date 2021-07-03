#!/bin/bash

set -e

echo "HADOOP_HOME=$HADOOP_HOME"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

echo "Starting JournalNode..."
hdfs --daemon start journalnode
echo "JournalNode started."

echo "Starting StandbyNode..."
hdfs namenode -bootstrapStandby
echo "StandbyNode started."

echo "Starting NameNode..."
hadoop-daemon.sh --script hdfs start namenode
echo "NameNode started."

echo "Starting DataNode..."
hadoop-daemon.sh --script hdfs start datanode
echo "DataNode started."

echo "Starting NodeManager..."
yarn nodemanager
echo "NodeManager started."

SIGINT=2
SIGTERM=15
stop()
{
  hadoop-daemon.sh --script hdfs stop datanode
  hadoop-daemon.sh --script hdfs stop namenode
  hadoop-daemon.sh --script stop journalnode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
