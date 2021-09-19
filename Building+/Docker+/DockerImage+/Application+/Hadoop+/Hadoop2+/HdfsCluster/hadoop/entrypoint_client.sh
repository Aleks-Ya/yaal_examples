#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

sleep 15s

echo "report..."
hdfs dfsadmin -report
echo "report done"

echo "hdfs ls..."
hdfs dfs -ls /
echo "hdfs ls done"

trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
