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
hadoop-daemon.sh --script hdfs start datanode
yarn nodemanager

SIGINT=2
SIGTERM=15
stop()
{
  hadoop-daemon.sh --script hdfs stop datanode
  hadoop-daemon.sh --script stop journalnode
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
