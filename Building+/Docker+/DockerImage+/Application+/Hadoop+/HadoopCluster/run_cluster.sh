#!/bin/bash

docker run --rm -it -p 8088:8088 -p 50070:50070 -p 50075:50075 --name hadoop-yarn-single hadoop-yarn-single-node-cluster
