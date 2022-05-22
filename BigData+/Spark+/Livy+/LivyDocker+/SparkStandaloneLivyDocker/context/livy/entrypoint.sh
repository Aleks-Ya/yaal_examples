#!/bin/bash

set -e

echo "SPARK_HOME=$SPARK_HOME"
echo "PWD=$PWD"

. user.sh
create_user root livy
chmod -R g+w /tmp

echo "Starting Livy..."
chown -R livy:root ${LIVY_HOME}/
su livy -c "${LIVY_HOME}/bin/livy-server start"
echo "Livy started."

SIGINT=2
SIGTERM=15
stop()
{
  su livy -c "${LIVY_HOME}/bin/livy-server stop"
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
