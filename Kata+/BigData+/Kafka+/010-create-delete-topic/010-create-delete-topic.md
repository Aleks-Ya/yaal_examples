# 010-create-delete-topic

## Task
Create and delete a Kafka topic using Kafka CLI.

## Steps
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure a new terminal:
	```shell
	set -x
	export TOPIC=kata-topic-create-delete-topic
	source ~/pr/home/yaal_examples/CLI/Kafka/KafkaCLI/KafkaCLI.sh
	```
3. Create a topic: 
	1. Create: `kafka-topics --create --topic $TOPIC`
	2. Verify: `kafka-topics --list`
4. Delete the topic: 
	1. Delete: `kafka-topics --delete --topic $TOPIC`
	2. Verify: `kafka-topics --list`

## Cleanup
1. Close the terminal
2. Stop the cluster

## History
- 2026-02-13 success
