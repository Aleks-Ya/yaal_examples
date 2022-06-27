#!/bin/bash

export HADOOP_VERSION=$1
export HADOOP_IMAGE_VERSION=$2
export SPARK_VERSION=$3
export IMAGE_VERSION=$4

if [[ -z ${HADOOP_VERSION} ]] || [[ -z ${HADOOP_IMAGE_VERSION} ]] || [[ -z ${SPARK_VERSION} ]] || [[ -z ${IMAGE_VERSION} ]]; then
  echo 'Error: wrong parameters. Example: "./build.sh 3.2.2 1.0 3.1.2 1.0"'
  exit 1
fi
if [[ $HADOOP_VERSION == 3* ]];  then
	export HADOOP_BUILD_VERSION=3
else
	export HADOOP_BUILD_VERSION=2.7
fi

echo "Hadoop image: ${HADOOP_IMAGE}"
echo "Spark version: ${SPARK_VERSION}"
echo "Hadoop build version: ${HADOOP_BUILD_VERSION}"
echo "This image version: ${IMAGE_VERSION}"

docker build \
  --build-arg HADOOP_IMAGE=hadoop-base-image-${HADOOP_VERSION}:${HADOOP_IMAGE_VERSION} \
  --build-arg SPARK_VERSION=${SPARK_VERSION} \
  --build-arg HADOOP_BUILD_VERSION=${HADOOP_BUILD_VERSION} \
  -t spark-base-image-${SPARK_VERSION}-hadoop-${HADOOP_VERSION}:${IMAGE_VERSION} \
  .
