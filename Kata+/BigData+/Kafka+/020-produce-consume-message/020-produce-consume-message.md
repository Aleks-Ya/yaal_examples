# 020-produce-consume-message

## Task
Create a Kafka topic, produce a message, consume a message, and delete the topic (using Kafka CLI).

## Setup
1. Start a cluster `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure Kafka CLI: `CLI/Kafka/KafkaCLI/KafkaCLI.md`
3. Create a topic: `kafka-topics --create --topic topic1`
4. Produce messages: `kafka-console-producer --topic topic1`
5. Consume messages: `kafka-console-consumer --from-beginning --topic topic1`

## Cleanup
1. Stop the cluster
