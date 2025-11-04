# 030-several-consumers

## Task
Consume messages from a topic by two consumers.

## Steps
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure Kafka CLI: `CLI/Kafka/KafkaCLI/KafkaCLI.md`
3. Create a topic: `kafka-topics --create --topic topic1`
4. Start consumers:
	1. Different consumer groups:
		1. Consumer 1: `kafka-console-consumer --topic=topic1 --group group1`
		2. Consumer 2: `kafka-console-consumer --topic=topic1 --group group2`
	2. The same consumer group:
		1. Consumer 3: `kafka-console-consumer --topic=topic1 --group group3`
		2. Consumer 4: `kafka-console-consumer --topic=topic1 --group group3`
5. Start producer: `kafka-console-producer --topic topic1`
6. Print test messages to the producer

## Cleanup
1. Stop the cluster
