#!/bin/bash

set -e

# Builds the jar.
mvn clean package
LOCAL_DIR=/tmp/hadoop-cluster-client-bind
mkdir -p ${LOCAL_DIR}
cp target/YarnApplication-1.jar ${LOCAL_DIR}/YarnApplication.jar
cp launch.sh ${LOCAL_DIR}/
