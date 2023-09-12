# Use bitnami Kafka Docker image

## Sources
Docker Hub: https://hub.docker.com/r/bitnami/kafka

## Client location in container
/opt/bitnami/kafka/bin/kafka-topics.sh
/opt/bitnami/kafka/bin/windows/kafka-topics.bat

## Run Kafka v3 (Kraft, without ZooKeeper)
Kafka only: `docker compose -f docker-compose-v35.yml up`
Test: `/home/aleks/installed/kafka_2.12-3.5.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`
Remove with volumes: `docker compose -f docker-compose-v35.yml down -v`
Explore the image: `docker run -it --rm docker.io/bitnami/kafka:3.5 bash`

## Run Kafka v2 (Kraft, without ZooKeeper)
Kafka only: `docker compose -f docker-compose-v28.yml up`
Test: `/home/aleks/installed/kafka_2.12-2.8.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`
Remove with volumes: `docker compose -f docker-compose-v28.yml down -v`
Explore the image: `docker run -it --rm docker.io/bitnami/kafka:2.8 bash`
