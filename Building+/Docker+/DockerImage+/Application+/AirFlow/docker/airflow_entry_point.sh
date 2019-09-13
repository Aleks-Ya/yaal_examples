#!/usr/bin/env bash

/entrypoint.sh webserver &
sleep 10 # wait while initdb finishes
airflow variables -s user airflow
airflow variables -s dag_profile_data 'MAIN dag data'
airflow variables -s dag_profile_data_stage 'STAGE dag data'
/entrypoint.sh scheduler --daemon
