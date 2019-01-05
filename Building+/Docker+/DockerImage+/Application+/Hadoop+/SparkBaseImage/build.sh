#!/bin/bash

export HADOOP_VERSION=$1
export HADOOP_IMAGE_VERSION=$2
export SPARK_VERSION=$3
export IMAGE_VERSION=$4

if [[ -z ${HADOOP_VERSION} ]] || [[ -z ${HADOOP_IMAGE_VERSION} ]] || [[ -z ${SPARK_VERSION} ]] || [[ -z ${IMAGE_VERSION} ]]; then
  echo 'Error: wrong parameters. Example: "./build.sh 2.9.2 1.1 2.4.0 1.0"'
  exit 1
fi

echo "Hadoop image: ${HADOOP_IMAGE}"
echo "Spark version: ${SPARK_VERSION}"
echo "This image version: ${IMAGE_VERSION}"

docker build \
  --build-arg HADOOP_IMAGE=hadoop-base-image-${HADOOP_VERSION}:${HADOOP_IMAGE_VERSION} \
  --build-arg SPARK_VERSION=${SPARK_VERSION} \
  -t spark-${SPARK_VERSION}-hadoop-${HADOOP_VERSION}-base-image:${IMAGE_VERSION} \
  .
