#!/bin/bash

set -e
echo "Running YARN application..."
${HADOOP_PREFIX}/bin/hdfs dfs -mkdir -p /user/$USER
${HADOOP_PREFIX}/bin/hdfs dfs -mkdir -p input
${HADOOP_PREFIX}/bin/hdfs dfs -rm -rf input
${HADOOP_PREFIX}/bin/hdfs dfs -put ./etc/hadoop/* input/
${HADOOP_PREFIX}/bin/hadoop jar ${HADOOP_PREFIX}/share/hadoop/mapreduce/hadoop-mapreduce-examples.jar grep ./input ./output 'dfs[a-z.]+'
${HADOOP_PREFIX}/bin/hdfs dfs -get output ./output
cat output/*
