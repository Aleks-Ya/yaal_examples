#!/bin/bash

set -e
/usr/sbin/sshd
ssh -o StrictHostKeyChecking=no localhost cat /dev/null
ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null
bin/hdfs namenode -format
./sbin/start-dfs.sh
bin/hdfs dfs -mkdir -p /user/$USER
bin/hdfs dfs -mkdir -p input
./sbin/start-yarn.sh

SIGINT=2
SIGTERM=15
stop()
{
  ./sbin/stop-yarn.sh
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker stop hadoop-yarn-single" to exit'
while true; do :; done
