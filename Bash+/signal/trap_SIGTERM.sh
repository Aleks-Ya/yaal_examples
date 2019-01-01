#!/bin/bash

SIGTERM=15
trap cleanup $SIGTERM

cleanup()
{
  echo "Got SIGTERM (code $SIGTERM)"
  exit 0
}

echo "To finish execute 'kill -SIGTERM $$'"
while true; do :; done
