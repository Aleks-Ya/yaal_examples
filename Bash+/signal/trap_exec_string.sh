#!/bin/bash

# Execute a command instead of invoking a method.

SIGINT=2
trap "echo 'Got SIGINT (code $SIGINT)' && exit 0" $SIGINT

echo "Press Ctrl-C or execute 'kill -SIGINT $$'"
while true; do :; done
