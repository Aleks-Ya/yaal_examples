#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

useradd -m -g root hdfs
useradd -m -g root yarn
useradd -m -g root spark
chmod -R g+w /tmp/logs

echo "Starting HDFS..."
su hdfs -c "hadoop-daemon.sh start journalnode"
su hdfs -c "hdfs namenode -format -nonInteractive"
su hdfs -c "hadoop-daemon.sh --script hdfs start namenode"
su hdfs -c "hadoop-daemon.sh --script hdfs start datanode"
su hdfs -c "hdfs haadmin -transitionToActive --forceactive nn1"
echo "HDFS started."

echo "Starting YARN..."
su yarn -c "start-yarn.sh"
echo "YARN started."

echo "Starting Spark..."
chown -R spark:root /opt/spark/
su hdfs -c "hdfs dfs -mkdir -p /shared/spark-logs"
su hdfs -c "hdfs dfs -chown -R spark /shared/spark-logs"
su spark -c "start-history-server.sh"
echo "Spark started."

SIGINT=2
SIGTERM=15
stop()
{
  su spark -c "stop-history-server.sh"
  su yarn -c "stop-yarn.sh"
  su hdfs -c "hadoop-daemon.sh --script hdfs stop namenode"
  su hdfs -c "hadoop-daemon.sh --script hdfs stop datanode"
  su hdfs -c "hadoop-daemon.sh stop journalnode"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
