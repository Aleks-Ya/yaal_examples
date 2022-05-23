#!/bin/bash

export HADOOP_BUILD_VERSION=2.7
export SPARK_VERSION=2.4.8
export LIVY_VERSION=0.7.1

echo "Hadoop build version: ${HADOOP_BUILD_VERSION}"
echo "Spark version: ${SPARK_VERSION}"
echo "Livy version: ${LIVY_VERSION}"

docker-compose build \
	--build-arg HADOOP_BUILD_VERSION=$HADOOP_BUILD_VERSION \
	--build-arg SPARK_VERSION=$SPARK_VERSION \
	--build-arg LIVY_VERSION=$LIVY_VERSION
