#!/usr/bin/env bash

echo Print command before execution

set -o xtrace
echo Turned trace on

echo "Hello, World!"
ls -l 

set +o xtrace
echo Turned trace off
