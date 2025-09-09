# 040-resume-consuming

## Task
Stop a consumer and resume it from the same offset.

## Setup
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Configure Kafka CLI: `CLI/Kafka/KafkaCLI/KafkaCLI.md`
3. Create a topic: `kafka-topics --create --topic topic1`
4. Initiate consuming:
	1. Start consumer: `kafka-console-consumer --topic=topic1 --consumer-property client.id=client1 --group group1`
	2. Produce message 1: `echo message1 | kafka-console-producer --topic topic1`
	3. Verify consumer lag (is `0`): `kafka-consumer-groups --describe --all-groups`
5. Pause consuming:
	1. Stop the consumer (Ctrl-C)
	2. Produce message 2: `echo message2 | kafka-console-producer --topic topic1`
	3. Verify consumer lag (is `1`): `kafka-consumer-groups --describe --all-groups`
6. Resume consuming:
	1. Resume consumer: `kafka-console-consumer --topic=topic1 --consumer-property client.id=client1 --group group1`
	2. Verify consumer lag (is `0`): `kafka-consumer-groups --describe --all-groups`

## Cleanup
1. Stop the cluster
