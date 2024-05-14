#!/bin/bash

set -e

echo "SPARK_HOME=$SPARK_HOME"
echo "SPARK_VERSION=$SPARK_VERSION"
echo "PWD=$PWD"

. user.sh
create_user root spark
chmod -R g+w /tmp

echo "Starting Spark Master..."
chown -R spark:root /opt/spark/
su spark -c "start-master.sh"
echo "Spark Master started."

echo "Starting Spark Connect..."
su spark -c "start-connect-server.sh \
--jars $SPARK_HOME/spark-connect.jar \
--driver-cores 1 \
--executor-cores 1 \
--total-executor-cores 1 \
--num-executors 1 \
--master spark://spark-standalone-cluster-master:7077"
echo "Spark Connect started."

echo "Starting Spark History Server..."
su spark -c "mkdir -p /shared/spark-events"
su spark -c "start-history-server.sh"
echo "Spark History Server started."

SIGINT=2
SIGTERM=15
stop()
{
  su spark -c "stop-history-server.sh"
  su spark -c "stop-master.sh"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
