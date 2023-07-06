# Use bitnami Kafka Docker image

## Sources
Docker Hub: https://hub.docker.com/r/bitnami/kafka

## Run with `docker run`
`docker run -d --name bitnami-kafka -e ALLOW_PLAINTEXT_LISTENER=yes --network=host docker.io/bitnami/kafka`

## Run with Docker Compose
Kafka only: `docker compose -f docker-compose_kafka-only.yml up`
Kafka and Zookeeper: `docker compose -f docker-compose_kafka-zookeeper.yml up`

## Test
List topics: `kafka-topics.sh --bootstrap-server localhost:9092 --list`
