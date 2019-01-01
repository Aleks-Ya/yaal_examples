#!/bin/bash

# Catch Ctrl-C

SIGINT=2
trap cleanup $SIGINT

cleanup()
{
  echo "Got SIGINT (code $SIGINT)"
  exit 0
}

echo "Press Ctrl-C or execute 'kill -SIGINT $$'"
while true; do :; done
