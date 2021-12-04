#!/bin/bash

set -e

echo "JAVA_HOME=$JAVA_HOME"
echo "HADOOP_HOME=$HADOOP_HOME"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

echo "Starting JournalNode..."
hdfs --daemon start journalnode
echo "JournalNode started."

echo "Formatting NameNode..."
hdfs namenode -format -nonInteractive
echo "NameNode is formatted."

echo "Starting NameNode..."
hdfs --daemon start namenode
echo "NameNode started."

echo "Starting DataNode..."
hdfs --daemon start datanode
echo "DataNode started."

echo "Activating NameNode..."
hdfs haadmin -transitionToActive --forceactive nn1
echo "NameNode is activated."

echo "Starting YARN..."
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root
start-yarn.sh
echo "YARN started."

echo "Starting SparkHistoryServer..."
hdfs dfs -mkdir -p /shared/spark-logs
start-history-server.sh
echo "SparkHistoryServer started"

SIGINT=2
SIGTERM=15
stop()
{
  stop-yarn.sh
  hdfs --daemon stop datanode
  hdfs --daemon stop namenode
  hdfs --daemon stop journalnode
  stop-history-server.sh
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
