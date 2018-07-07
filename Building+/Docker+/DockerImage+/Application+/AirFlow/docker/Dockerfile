# Build: docker build --network default -t airflow:1 .
# Run: docker run -d \
#  --env AIRFLOW__CORE__FERNET_KEY="HSOUU4w4_BcmkSMIE1a8VCrO3WCb3EbJw3OfH8IV1tM=" \
#  --net bridge \
#  --name airflow \
#  --mount type=bind,source="$(pwd)"/dags,target=/usr/local/airflow/dags \
#  -p 8080:8080 \
#  iablokov/airflow:1

FROM puckel/docker-airflow:1.9.0

USER root
RUN set -e && \
    apt-get update && \
    apt-get install -y procps nano

USER airflow
ADD airflow_entry_point.sh /airflow_entry_point.sh

ENTRYPOINT ["/airflow_entry_point.sh"]
