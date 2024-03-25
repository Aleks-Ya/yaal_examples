#!/bin/bash

source .env

echo "Scala binary version: ${SCALA_BINARY_VERSION}"
echo "Spark version: ${SPARK_VERSION}"
echo "Hadoop build version: ${HADOOP_BUILD_VERSION}"
echo "Tag: ${TAG}"

docker build \
  --build-arg SCALA_BINARY_VERSION="$SCALA_BINARY_VERSION" \
  --build-arg SPARK_VERSION="$SPARK_VERSION" \
  --build-arg HADOOP_BUILD_VERSION="$HADOOP_BUILD_VERSION" \
  -t "$TAG" \
  context
