#!/bin/bash

docker run --rm -it -p 8088:8088 --name hadoop-yarn-single hadoop-yarn-single-node-cluster
