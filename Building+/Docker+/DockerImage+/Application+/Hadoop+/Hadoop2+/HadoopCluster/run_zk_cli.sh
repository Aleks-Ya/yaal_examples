#!/bin/bash

docker run --rm -it \
  --network hadoop-cluster-network \
  --name hadoop-cluster-zk-client \
  zookeeper \
  zkCli.sh -server zookeeper-1,zookeeper-2
