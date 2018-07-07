#!/usr/bin/env bash

/entrypoint.sh webserver &
sleep 10 # wait while initdb finishes
airflow variables -s user airflow
airflow scheduler --daemon
