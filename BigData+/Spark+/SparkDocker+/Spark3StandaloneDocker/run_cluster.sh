#!/bin/bash

mkdir -p /tmp/spark-standalone-cluster-shared
docker-compose down -v
docker-compose up
