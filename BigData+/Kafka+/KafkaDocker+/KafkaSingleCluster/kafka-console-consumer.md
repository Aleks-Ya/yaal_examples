# kafka-console-consumer CLI

Location: `$KAFKA_HOME/bin/kafka-console-consumer.sh`

## Consume messages from the topic
1. Start Console consumer: `kafka-console-consumer.sh --bootstrap-server=$(broker-list.sh) --topic=my-topic --from-beginning`
2. Stop Console consumer: Ctrl-C

## Set consumer group
`kafka-console-consumer.sh --bootstrap-server=$(broker-list.sh) --topic=my-topic --from-beginning --group my-group`
