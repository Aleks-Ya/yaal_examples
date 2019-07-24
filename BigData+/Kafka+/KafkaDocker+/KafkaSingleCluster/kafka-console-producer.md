# kafka-console-producer CLI

Location: `$KAFKA_HOME/bin/kafka-console-producer.sh`

## Add messages to the topic
1. Start Console producer: `kafka-console-producer.sh --broker-list=$(broker-list.sh) --topic=my-topic`
2. Stop Console producer: Ctrl-C

kafka-consumer-groups.sh --zookeeper $ZK --list
