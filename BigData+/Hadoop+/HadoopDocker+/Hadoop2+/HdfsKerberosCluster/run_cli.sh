#!/bin/bash

HOST_DIR=/tmp/hdfs-kerberos-cluster-client-bind/
CONTAINER_DIR=/tmp/host-bind/
mkdir -p $HOST_DIR
docker run --rm -it \
  --network hdfs-kerberos-cluster-network \
  --name hdfs-kerberos-cluster-client \
  --mount type=bind,source=$HOST_DIR,target=$CONTAINER_DIR \
  --workdir $CONTAINER_DIR \
  hdfs-kerberos-cluster-master \
  bash
