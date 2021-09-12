#!/bin/bash

set -e

USER="$(whoami)"
echo "User: $USER"

LOCAL_DIR=/tmp/app
echo "Local working dir: ${LOCAL_DIR}"
rm -rf ${LOCAL_DIR}
mkdir -p ${LOCAL_DIR}/input
mkdir -p ${LOCAL_DIR}/output
cp "${HADOOP_PREFIX}"/etc/hadoop/*.xml "${LOCAL_DIR}"/input

HDFS_DIR=/tmp/hadoop-cluster-master
echo "HDFS working dir: ${HDFS_DIR}"
hdfs dfs -rm -r -f ${HDFS_DIR}
hdfs dfs -mkdir -p ${HDFS_DIR}/input
hdfs dfs -put "${HADOOP_PREFIX}"/etc/hadoop/* "${HDFS_DIR}"/input/

hdfs dfs -mkdir -p /user/"${USER}"

echo "Running YARN application..."
hadoop jar "${HADOOP_PREFIX}"/share/hadoop/mapreduce/hadoop-mapreduce-examples-"${HADOOP_VERSION}".jar \
  grep "${HDFS_DIR}"/input "${HDFS_DIR}"/output 'dfs[a-z.]+'

hdfs dfs -get ${HDFS_DIR}/output/* ${LOCAL_DIR}/output/
cat ${LOCAL_DIR}/output/*
