#!/bin/bash

HOST_DIR=/tmp/hadoop3-cluster-client-bind/
CONTAINER_DIR=/tmp/host-bind/
mkdir -p $HOST_DIR
docker run --rm -it \
  --network hadoop3-cluster-network \
  --name hadoop3-cluster-client \
  --mount type=bind,source=$HOST_DIR,target=$CONTAINER_DIR \
  --workdir $CONTAINER_DIR \
  hadoop3-cluster-master \
  bash
