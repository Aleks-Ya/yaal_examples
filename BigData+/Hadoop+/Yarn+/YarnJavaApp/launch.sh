#!/bin/bash

set -e

echo "Putting the app jar into HDFS..."
hdfs dfs -rm -r -f /apps
hdfs dfs -mkdir -p /apps
hdfs dfs -copyFromLocal /tmp/YarnApplication.jar /apps/YarnApplication.jar

echo "Executing the app..."
yarn jar /tmp/YarnApplication.jar yarn.Client
