#!/bin/bash

export SPARK_VERSION=3.2.1
export TAG=spark-standalone

echo "Spark version: ${SPARK_VERSION}"
echo "Tag: ${TAG}"

docker build --build-arg SPARK_VERSION="$SPARK_VERSION" -t "$TAG" context
