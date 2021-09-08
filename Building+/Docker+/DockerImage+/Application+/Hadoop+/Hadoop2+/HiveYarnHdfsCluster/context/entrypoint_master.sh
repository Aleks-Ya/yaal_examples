#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"

useradd -m -g root hdfs
useradd -m -g root yarn
chmod -R g+w /tmp
chmod -R g+w /opt/hadoop

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

echo "Starting Hive..."
su hdfs -c "hdfs dfs -mkdir -p /user/hive/warehouse"
su hdfs -c "hdfs dfs -chown -R hive /user/hive/warehouse"
su hdfs -c "hdfs dfs -mkdir -p /tmp/hive"
su hdfs -c "hdfs dfs -chown -R hive /tmp/hive"
su hdfs -c "hdfs dfs -chmod -R a+w /tmp/hive"
su hive -c "PGPASSWORD=the_postgres_pass psql -h postgres -p 5432 -U postgres -c 'CREATE DATABASE hive'"
su hive -c "schematool -dbType postgres -initSchema -userName postgres -passWord the_postgres_pass"
su hive -c "hiveserver2"
echo "Hive started."

SIGINT=2
SIGTERM=15
stop()
{
  su yarn -c "stop-yarn.sh"
  su hdfs -c "hadoop-daemon.sh --script hdfs stop namenode"
  su hdfs -c "hadoop-daemon.sh --script hdfs stop datanode"
  su hdfs -c "hadoop-daemon.sh stop journalnode"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
