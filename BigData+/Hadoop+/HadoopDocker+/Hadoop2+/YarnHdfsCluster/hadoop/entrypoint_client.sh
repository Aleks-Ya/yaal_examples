#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

sleep 20s

echo "HDFS report..."
hdfs dfsadmin -report
echo "HDFS report is done"

echo "HDFS ls..."
hdfs dfs -ls /
echo "HDFS ls is done"

echo "YARN node list..."
yarn node -list -all
echo "YARN node list is done"

trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
