#!/bin/bash

set -e

# Puts the jar in HDFS under /apps/.
hadoop fs -rm -r -f /apps
hadoop fs -mkdir -p /apps
hadoop fs -copyFromLocal YarnApplication.jar /apps/YarnApplication.jar

echo "Executes the Client"
hadoop jar YarnApplication.jar com.gpiskas.yarn.Client

# Outputs the whole log of the last app.
#last=`ls -1t $HADOOP_HOME/logs/userlogs/ | head -1`
#cat $HADOOP_HOME/logs/userlogs/"$last"/*/*
