#!/bin/bash

set -e

echo "SPARK_HOME=$SPARK_HOME"
echo "PWD=$PWD"

. user.sh
create_user root spark
chmod -R g+w /tmp

echo "Starting Spark Worker..."
chown -R spark:root /opt/spark/
su spark -c "start-worker.sh spark://spark-standalone-cluster-master:7077"
echo "Spark Worker started."

echo "Creating dir for Spark History Server..."
su spark -c "mkdir -p /shared/spark-events"

SIGINT=2
SIGTERM=15
stop()
{
  su spark -c "stop-worker.sh"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
