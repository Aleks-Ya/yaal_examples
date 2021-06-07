#!/bin/bash

set -e
/usr/sbin/sshd
ssh -o StrictHostKeyChecking=no localhost cat /dev/null
ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null
ssh -o StrictHostKeyChecking=no master-service cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-1 cat /dev/null
ssh -o StrictHostKeyChecking=no slave-service-2 cat /dev/null

SIGINT=2
SIGTERM=15

yarn nodemanager

stop()
{
  exit 0
}
trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
