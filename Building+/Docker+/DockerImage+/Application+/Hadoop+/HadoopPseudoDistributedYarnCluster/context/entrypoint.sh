#!/bin/bash

set -e
/usr/sbin/sshd
ssh -o StrictHostKeyChecking=no localhost cat /dev/null
ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null
${HADOOP_PREFIX}/bin/hdfs namenode -format
${HADOOP_PREFIX}/sbin/start-dfs.sh
${HADOOP_PREFIX}/bin/hdfs dfs -mkdir -p /user/$USER
${HADOOP_PREFIX}/bin/hdfs dfs -mkdir -p input
${HADOOP_PREFIX}/sbin/start-yarn.sh

SIGINT=2
SIGTERM=15
stop()
{
  ${HADOOP_PREFIX}/sbin/stop-yarn.sh
  ${HADOOP_PREFIX}/sbin/stop-dfs.sh
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker stop hadoop-yarn-single" to exit'
while true; do :; done
