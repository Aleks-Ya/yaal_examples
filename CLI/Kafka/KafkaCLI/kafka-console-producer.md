# kafka-console-producer CLI

Location: `$KAFKA_HOME/bin/kafka-console-producer.sh`

Help: `kafka-console-producer.sh --help`

## Produce messages to the topic

### Produce message and stop
`echo "my message" | kafka-console-producer.sh --bootstrap-server localhost:9092 --topic=topic1`

### Produce message without key
1. Start Console producer: `kafka-console-producer.sh --bootstrap-server localhost:9092 --topic=topic1`
2. Print value
3. Stop Console producer: Ctrl-C

### Produce message with key
1. Start Console producer: 
```shell
kafka-console-producer.sh \
	--bootstrap-server localhost:9092 \
	--topic=topic1 \
	--property "parse.key=true" \
	--property "key.separator=:"
```
2. Print key and value: `key:value`
3. Stop Console producer: Ctrl-C

### With a header (Kafka >= 3.1)
1. Start the producer:
```shell
kafka-console-producer.sh \
	--bootstrap-server localhost:9092 \
	--topic=topic1 \
	--property "parse.headers=true"
```
2. Produce message with headers (`\t` separates headers from the message):
```
header1:value1,header2:value2	my_message
```



---

# Deprecated version (using `--broker-list`)
## Add messages to the topic

### Add message without key
1. Start Console producer: `kafka-console-producer.sh --broker-list=$(broker-list.sh) --topic=topic1`
1. Print value
1. Stop Console producer: Ctrl-C

### Add message with key
1. Start Console producer: 
```shell
kafka-console-producer.sh \
	--broker-list=$(broker-list.sh) \
	--topic=topic1 \
	--property "parse.key=true" \
	--property "key.separator=:"
```
1. Print key and value: `key:value`
1. Stop Console producer: Ctrl-C

### With a header (Kafka >= 3.1)
1. Start the producer:
```shell
kafka-console-producer.sh \
	--broker-list=$(broker-list.sh) \
	--topic=topic1 \
	--property "parse.headers=true"
```
2. Add message with headers (`\t` separates headers from the message):
```
header1:value1,header2:value2	my_message
```