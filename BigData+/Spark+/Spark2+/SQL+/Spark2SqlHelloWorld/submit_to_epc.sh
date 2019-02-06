#!/usr/bin/env bash

set -e

if [[ "$1" != "--no-build" ]]
then
    mvn clean package -DskipTests
fi

cp -fr scripts/* target/
cp -fr /home/aleks/Dropbox/ahml/hadoop-conf target/

docker run -it \
    --env HADOOP_CONF_DIR="/usr/local/airflow/hadoop-conf" \
    --mount type=bind,source=${PWD}/target,target=/usr/local/airflow/integration/emiss \
    --mount type=bind,source=${PWD}/target/hadoop-conf,target=/usr/local/airflow/hadoop-conf \
    --entrypoint "/usr/local/airflow/integration/emiss/submit.sh" \
    registry.ahml-infr.projects.epam.com:32505/container-airflow:1.9.0.15
