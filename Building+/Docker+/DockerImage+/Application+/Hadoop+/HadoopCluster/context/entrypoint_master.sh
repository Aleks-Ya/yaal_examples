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

hadoop-daemon.sh start journalnode
hdfs namenode -format -nonInteractive
hadoop-daemon.sh --script hdfs start namenode
hadoop-daemon.sh --script hdfs start datanode
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
