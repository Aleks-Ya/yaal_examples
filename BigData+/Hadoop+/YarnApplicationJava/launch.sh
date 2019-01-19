#!/bin/bash

set -e

# Puts the jar in HDFS under /apps/.
hdfs dfs -rm -r -f /apps
hdfs dfs -mkdir -p /apps
hdfs dfs -copyFromLocal YarnApplication.jar /apps/YarnApplication.jar

echo "Executes the Client"
yarn jar YarnApplication.jar yarn.Client

# Outputs the whole log of the last app.
#last=`ls -1t $HADOOP_HOME/logs/userlogs/ | head -1`
#cat $HADOOP_HOME/logs/userlogs/"$last"/*/*
