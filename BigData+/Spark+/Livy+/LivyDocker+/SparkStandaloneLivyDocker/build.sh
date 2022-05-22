#!/bin/bash

export SPARK_VERSION=3.2.1
export LIVY_VERSION=0.7.1

echo "Spark version: ${SPARK_VERSION}"
echo "Livy version: ${LIVY_VERSION}"

docker-compose build --build-arg SPARK_VERSION=$SPARK_VERSION --build-arg LIVY_VERSION=$LIVY_VERSION
