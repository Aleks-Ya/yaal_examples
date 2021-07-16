#!/bin/bash

echo 'Start wating'

SIGINT=2
SIGTERM=15

trap "echo 'Got a signal' && exit 0" $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker stop action-on-container-stop"'
while true; do :; done
