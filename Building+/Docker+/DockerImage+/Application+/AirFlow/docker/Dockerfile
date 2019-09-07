FROM puckel/docker-airflow:1.9.0-4

USER root
RUN set -e && \
    apt-get update && \
    apt-get install -y procps nano net-tools

USER airflow
ADD airflow_entry_point.sh /airflow_entry_point.sh

ENV AIRFLOW__CORE__FERNET_KEY "HSOUU4w4_BcmkSMIE1a8VCrO3WCb3EbJw3OfH8IV1tM="

EXPOSE 8080

ENTRYPOINT ["/airflow_entry_point.sh"]
CMD []
