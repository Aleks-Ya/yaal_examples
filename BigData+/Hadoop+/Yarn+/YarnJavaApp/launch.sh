#!/bin/bash

set -e

echo "Putting the app jar into HDFS..."
hdfs dfs -rm -r -f /apps
hdfs dfs -mkdir -p /apps
hdfs dfs -copyFromLocal /tmp/YarnJavaApp.jar /apps/YarnJavaApp.jar

echo "Executing the app..."
yarn jar /tmp/YarnJavaApp.jar yarn.Client
