#!/bin/bash

export LIVY_VERSION=0.7.1
export TAG=spark-standalone

echo "Livy version: ${LIVY_VERSION}"
echo "Tag: ${TAG}"

docker build --build-arg LIVY_VERSION="$LIVY_VERSION" -t "$TAG" context
