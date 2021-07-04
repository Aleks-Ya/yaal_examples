#!/bin/bash

export HADOOP_VERSION=$1
export HADOOP_BASE_IMAGE_VERSION=$2
export HIVE_VERSION=$3
export HIVE_BASE_IMAGE_VERSION=$4

if [[ -z ${HADOOP_VERSION} ]] || [[ -z ${HADOOP_BASE_IMAGE_VERSION} ]] || [[ -z ${HIVE_VERSION} ]] || [[ -z ${HIVE_BASE_IMAGE_VERSION} ]]; then
  echo 'Error: wrong parameters. Example: "./build.sh 2.10.1 1.0 2.3.9 1.0"'
  exit 1
fi

export HADOOP_BASE_IMAGE="hadoop-base-image-${HADOOP_VERSION}:${HADOOP_BASE_IMAGE_VERSION}"
export HIVE_BASE_IMAGE="hive-base-image-${HIVE_VERSION}-hadoop-${HADOOP_VERSION}:${HIVE_BASE_IMAGE_VERSION}"

echo "HadoopBaseImage: ${HADOOP_BASE_IMAGE}"
echo "Hive version: ${HIVE_VERSION}"
echo "This image version (HiveBaseImage): ${HIVE_BASE_IMAGE_VERSION}"

docker build \
  --build-arg HADOOP_BASE_IMAGE="$HADOOP_BASE_IMAGE" \
  --build-arg HIVE_VERSION="$HIVE_VERSION" \
  -t "$HIVE_BASE_IMAGE" \
  .
