#!/bin/bash

HOST_DIR=/tmp/hdfs-ha-qjm-cluster-client-bind/
CONTAINER_DIR=/tmp/host-bind/
mkdir -p $HOST_DIR
docker run --rm -it \
  --network hdfs-ha-qjm-cluster-network \
  --name hdfs-ha-qjm-cluster-client \
  --mount type=bind,source=$HOST_DIR,target=$CONTAINER_DIR \
  --workdir $CONTAINER_DIR \
  hdfs-ha-qjm-cluster-master \
  bash
