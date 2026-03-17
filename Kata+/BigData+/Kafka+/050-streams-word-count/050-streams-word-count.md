# 050-streams-word-count

## Task
Run the standard WordCount application on Kafka Streams.
Docs: https://kafka.apache.org/41/streams/quickstart/#step-4-start-the-wordcount-application

## Steps
1. Start a cluster: `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`
2. Open a new terminal
3. Change current directory
4. Configure the terminal: `source envs.sh`
5. Create topics: 
	1. Create the input topic: `kafka-topics --create --topic $TOPIC_IN`
	2. Create the output topic: `kafka-topics --create --topic $TOPIC_OUT`
6. Start the WordCount app: `kafka-run-class org.apache.kafka.streams.examples.wordcount.WordCountDemo`
7. Start a consumer: 
	1. Open a new terminal and configure: `source envs.sh`
	2. Start consumer: 
		```shell
		kafka-console-consumer --from-beginning --topic=$TOPIC_OUT \
			--property print.key=true \
		    --property print.value=true \
		    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
		    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
		```
8. Produce messages: 
	1. Open a new terminal and configure: `source envs.sh`
	2. Produce message 1: `echo "word1 word2 word3" | kafka-console-producer --topic $TOPIC_IN`
	3. Produce message 2: `echo "word1 word2 word5" | kafka-console-producer --topic $TOPIC_IN`
9. Verify consumer output:
```shell
word1	1
word2	1
word3	1
word1	2
word2	2
word5	1
```

## Cleanup
1. Delete topics:
	```shell
	kafka-topics --delete --topic $TOPIC_IN
	kafka-topics --delete --topic $TOPIC_OUT
	kafka-topics --delete --topic streams-wordcount-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog
	kafka-topics --delete --topic streams-wordcount-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition
	```
2. Close 3 terminals
3. Stop the cluster

## History
- 2026-02-13 success
- 2026-03-23 success
