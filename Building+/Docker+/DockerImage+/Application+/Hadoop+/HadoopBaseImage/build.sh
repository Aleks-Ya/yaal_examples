#!/bin/bash

export HADOOP_VERSION=$1
export IMAGE_VERSION=$2

if [[ -z ${HADOOP_VERSION} ]]; then
  echo 'Error: No Hadoop version. Example: "./build.sh 2.9.2 1.0"'
  exit 1
fi

if [[ -z ${IMAGE_VERSION} ]]; then
  echo 'Error: No image version. Example: "./build.sh 2.9.2 1.0"'
  exit 1
fi

echo "Hadoop version: ${HADOOP_VERSION}"
echo "Image version: ${IMAGE_VERSION}"
docker build --build-arg HADOOP_VERSION=${HADOOP_VERSION} -t hadoop-base-image-${HADOOP_VERSION}:${IMAGE_VERSION} .
