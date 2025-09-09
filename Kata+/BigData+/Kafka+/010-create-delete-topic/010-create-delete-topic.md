# 010-create-delete-topic

## Task
Create and delete a Kafka topic using Kafka CLI.

## Setup
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure Kafka CLI: `CLI/Kafka/KafkaCLI/KafkaCLI.md`
3. Create a topic: 
	1. Create: `kafka-topics --create --topic topic1`
	2. Verify: `kafka-topics --list`
4. Delete the topic: 
	1. Delete: `kafka-topics --delete --topic topic1`
	2. Verify: `kafka-topics --list`

## Cleanup
1. Stop the cluster
