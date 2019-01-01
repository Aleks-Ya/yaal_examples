#!/bin/bash

# Print the trap commands associated with each SIGNAL_SPEC

SIGINT=2
SIGTERM=15
trap "echo Got a signal && exit 0" $SIGINT $SIGTERM

echo "Traps:"
trap -p
echo

echo "Press Ctrl-C to exit"
while true; do :; done
