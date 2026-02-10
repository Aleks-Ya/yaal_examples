# Official Kafka image

DockerHub: 
- https://hub.docker.com/r/apache/kafka
- Native: https://hub.docker.com/r/apache/kafka-native

## Run
1. Start broker: `docker run --rm --name broker --network host apache/kafka:latest`
2. Test: `kafka-broker-api-versions.sh --bootstrap-server localhost:9092`

See also: `CLI/Kafka/KafkaCLI/KafkaCLI.md`
