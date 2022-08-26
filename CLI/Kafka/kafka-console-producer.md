# kafka-console-producer CLI

Location: `$KAFKA_HOME/bin/kafka-console-producer.sh`

Help: `kafka-console-producer.sh --help`

## Add messages to the topic

### Add message without key
1. Start Console producer: `kafka-console-producer.sh --broker-list=$(broker-list.sh) --topic=my-topic`
1. Print value
1. Stop Console producer: Ctrl-C

### Add message with key
1. Start Console producer: 
```
kafka-console-producer.sh \
	--broker-list=$(broker-list.sh) \
	--topic=my-topic \
	--property "parse.key=true" \
	--property "key.separator=:"
```
1. Print key and value: `key:value`
1. Stop Console producer: Ctrl-C

### With a header (Kafka >= 3.1)
1. Start the producer:
```
kafka-console-producer.sh \
	--broker-list=$(broker-list.sh) \
	--topic=my-topic \
	--property "parse.headers=true"
```
2. Add message with headers (`\t` separates headers from the message):
```
header1:value1,header2:value2	my_message
```