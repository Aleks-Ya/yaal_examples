# 020-produce-consume-message

## Task
Create a Kafka topic, produce a message, consume a message, and delete the topic (using Kafka CLI).

## Steps
1. Start a cluster `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure a new terminal:
	```shell
	set -x
	export TOPIC=kata-topic-produce-consume-message
	source ~/pr/home/yaal_examples/CLI/Kafka/KafkaCLI/KafkaCLI.sh
	```
3. Create a topic: `kafka-topics --create --topic $TOPIC`
4. Produce messages: 
	1. Start a producer: `kafka-console-producer --topic $TOPIC`
	2. Enter `abc`, `xyz`, Ctrl-D
5. Consume messages: 
	1. Start a comsuter: `kafka-console-consumer --from-beginning --topic $TOPIC`
	2. Wait for messages: `abc`, `xyz`
	3. Stop the consumer: Ctrl-C

## Cleanup
1. Delete the topic: `kafka-topics --delete --topic $TOPIC`
2. Close the terminal
3. Stop the cluster

## History
- 2026-02-13 success
