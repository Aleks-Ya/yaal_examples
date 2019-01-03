#!/bin/bash

HOST_DIR=/tmp/yarn-cluster-client-bind/
CONTAINER_DIR=/tmp/host-bind/
mkdir -p $HOST_DIR
docker run --rm -it \
  --network yarn-cluster-network \
  --name yarn-cluster-client \
  --mount type=bind,source=$HOST_DIR,target=$CONTAINER_DIR \
  --workdir $CONTAINER_DIR \
  yarn-cluster-master \
  bash
