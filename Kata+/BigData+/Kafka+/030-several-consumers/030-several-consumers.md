# 030-several-consumers

## Task
Consume messages from a topic by two consumers (in the same and different consumer groups).

## Steps
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Open a new terminal
3. Change current directory
4. Configure the terminal: `source envs.sh`
5. Create a topic: `kafka-topics --create --topic $TOPIC`
6. Start consumers:
	1. Different consumer groups:
		1. Consumer 1: 
			1. Open a new terminal and configure: `source envs.sh`
			2. Start consumer: `kafka-console-consumer --topic=$TOPIC --group $GROUP1`
		2. Consumer 2: 
			1. Open a new terminal and configure: `source envs.sh`
			2. Start consumer: `kafka-console-consumer --topic=$TOPIC --group $GROUP2`
	2. The same consumer group:
		3. Consumer 3: 
			1. Open a new terminal and configure: `source envs.sh`
			2. Start consumer: `kafka-console-consumer --topic=$TOPIC --group $GROUP3`
		4. Consumer 4: 
			1. Open a new terminal and configure: `source envs.sh`
			2. Start consumer: `kafka-console-consumer --topic=$TOPIC --group $GROUP3`
7. Produce messages:
	1. Start producer: `kafka-console-producer --topic $TOPIC`
	2. Type: `abc`
	3. Stop the producer: Ctrl-D

## Cleanup
1. Close 5 terminals
2. Stop the cluster

## History
- 2026-02-13 success
