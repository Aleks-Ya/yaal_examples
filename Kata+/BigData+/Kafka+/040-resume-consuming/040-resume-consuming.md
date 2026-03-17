# 040-resume-consuming

## Task
Stop a consumer and resume it from the same offset.

## Steps
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Open a new terminal
3. Change current directory
4. Configure the terminal: `source envs.sh`
5. Create a topic: `kafka-topics --create --topic $TOPIC`
6. Initiate consuming:
	1. Start consumer: 
		1. Open a new terminal and configure: `source envs.sh`
		2. Start consumer: `kafka-console-consumer --topic=$TOPIC --consumer-property client.id=$CLIENT --group $GROUP`
	2. Produce message 1: 
		1. Open a new terminal and configure: `source envs.sh`
		2. Produce message: `echo message1 | kafka-console-producer --topic $TOPIC`
	3. Verify consumer lag (is `0`): `kafka-consumer-groups --describe --group $GROUP`
7. Pause consuming:
	1. Stop the consumer (Ctrl-C)
	2. Produce message 2: `echo message2 | kafka-console-producer --topic $TOPIC`
	3. Verify consumer lag (is `1`): `kafka-consumer-groups --describe --group $GROUP`
8. Resume consuming:
	1. Resume consumer: `kafka-console-consumer --topic=$TOPIC --consumer-property client.id=$CLIENT --group $GROUP`
	2. Verify consumer lag (is `0`): `kafka-consumer-groups --describe --group $GROUP`

## Cleanup
1. Delete the topic: `kafka-topics --delete --topic $TOPIC`
2. Close 3 terminals
3. Stop the cluster

## History
- 2026-02-13 success
- 2026-03-23 success
