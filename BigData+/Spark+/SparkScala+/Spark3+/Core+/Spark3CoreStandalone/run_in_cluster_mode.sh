#!/bin/bash
set -e

if [ -z "$1" ]; then
  echo "Usage: $0 <main-class>"
  exit 1
fi
MAIN_CLASS="$1"

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 11.0.26-zulu

unset JAVA_HOME

spark-submit \
--class "$MAIN_CLASS" \
--master spark://spark-standalone-cluster-master:7077 \
--deploy-mode cluster \
--num-executors 2 \
--executor-cores 1 \
--conf "spark.eventLog.enabled=true" \
--conf "spark.eventLog.dir=file:/shared/spark-events" \
file:///shared/spark3corestandalone.jar