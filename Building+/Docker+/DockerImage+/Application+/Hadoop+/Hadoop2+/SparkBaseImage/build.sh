#!/bin/bash

export HADOOP_VERSION=$1
export HIVE_VERSION=$2
export HIVE_BASE_IMAGE_VERSION=$3
export SPARK_VERSION=$4
export SPARK_BASE_IMAGE_VERSION=$5

if [[ -z ${HADOOP_VERSION} ]] || [[ -z ${HIVE_BASE_IMAGE_VERSION} ]] || [[ -z ${SPARK_VERSION} ]] || [[ -z ${SPARK_BASE_IMAGE_VERSION} ]]; then
  echo 'Error: wrong parameters. Example: "./build.sh 2.10.1 2.3.9 1.0 2.4.8 1.0"'
  exit 1
fi
export HIVE_BASE_IMAGE="hive-base-image-${HIVE_VERSION}-hadoop-${HADOOP_VERSION}:${HIVE_BASE_IMAGE_VERSION}"
export SPARK_BASE_IMAGE="spark-base-image-${SPARK_VERSION}-hadoop-${HADOOP_VERSION}:${SPARK_BASE_IMAGE_VERSION}"

echo "Hadoop version: ${HADOOP_VERSION}"
echo "Hive version: ${HIVE_VERSION}"
echo "Spark version: ${SPARK_VERSION}"
echo "HiveBaseImage: ${HIVE_BASE_IMAGE}"
echo "This image version (SparkBaseImage): ${SPARK_BASE_IMAGE_VERSION}"

docker build \
  --build-arg HIVE_BASE_IMAGE="$HIVE_BASE_IMAGE" \
  --build-arg SPARK_VERSION="$SPARK_VERSION" \
  --build-arg HADOOP_BUILD_VERSION=2.7 \
  -t "$SPARK_BASE_IMAGE" \
  .
