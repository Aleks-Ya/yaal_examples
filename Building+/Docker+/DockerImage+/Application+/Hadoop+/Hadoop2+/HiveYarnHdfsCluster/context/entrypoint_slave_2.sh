#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

useradd -m -g root hdfs
useradd -m -g root yarn
chmod -R g+w /tmp/logs

echo "Starting HDFS..."
su hdfs -c "hadoop-daemon.sh start journalnode"
su hdfs -c "hadoop-daemon.sh --script hdfs start datanode"
echo "HDFS started."

echo "Starting YARN..."
su yarn -c "yarn nodemanager"
echo "YARN started."

SIGINT=2
SIGTERM=15
stop()
{
  stop-yarn.sh
  su hdfs -c "hadoop-daemon.sh --script hdfs stop datanode"
  su hdfs -c "hadoop-daemon.sh stop journalnode"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
