#!/bin/bash

set -e
echo "Running YARN application..."
bin/hdfs dfs -mkdir -p /user/$USER
bin/hdfs dfs -mkdir -p input
bin/hdfs dfs -put ./etc/hadoop/* input/
./bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples.jar grep ./input ./output 'dfs[a-z.]+'
bin/hdfs dfs -get output ./output
cat output/*
