#!/bin/bash

set -e

echo "HADOOP_HOME=$HADOOP_HOME"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

echo "Starting JournalNode..."
hadoop-daemon.sh start journalnode
echo "JournalNode started."

echo "Formatting NameNode..."
hdfs namenode -format -nonInteractive
echo "NameNode is formatted."

echo "Starting NameNode..."
hadoop-daemon.sh --script hdfs start namenode
echo "NameNode started."

echo "Starting DataNode..."
hadoop-daemon.sh --script hdfs start datanode
echo "DataNode started."

hdfs haadmin -transitionToActive --forceactive nn1
start-yarn.sh

# Start Spark History Server
hdfs dfs -mkdir -p /shared/spark-logs
start-history-server.sh

SIGINT=2
SIGTERM=15
stop()
{
  stop-yarn.sh
  hadoop-daemon.sh --script hdfs stop namenode
  hadoop-daemon.sh --script hdfs stop datanode
  hadoop-daemon.sh --script stop journalnode
  stop-history-server.sh
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
