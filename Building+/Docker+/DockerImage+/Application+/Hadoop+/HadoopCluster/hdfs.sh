#!/bin/bash

docker build -t hadoop-cluster-client context
docker run --rm -it --network hadoop-cluster-network --name hadoop-cluster-client hadoop-cluster-client bash
