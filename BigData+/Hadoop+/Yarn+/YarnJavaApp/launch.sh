#!/bin/bash

set -e

echo "Putting the jars into HDFS..."
hdfs dfs -rm -r -f /apps
hdfs dfs -mkdir -p /apps
hdfs dfs -copyFromLocal /tmp/container.jar /apps/container.jar
hdfs dfs -copyFromLocal /tmp/am.jar /apps/am.jar

echo "Executing the Client app..."
yarn jar /tmp/client.jar yarn.Client
