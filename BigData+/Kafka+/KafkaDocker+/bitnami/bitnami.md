# Use bitnami Kafka Docker image

## Sources
Docker Hub: https://hub.docker.com/r/bitnami/kafka

## Run Kafka v3
Kafka only: `docker compose -f docker-compose-v35.yml up`
Test: `/home/aleks/installed/kafka_2.12-3.5.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`

## Run Kafka v2
Kafka only: `docker compose -f docker-compose-v28.yml up`
Test: `/home/aleks/installed/kafka_2.12-2.8.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`
