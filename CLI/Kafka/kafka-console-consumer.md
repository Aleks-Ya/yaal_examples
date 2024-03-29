# kafka-console-consumer.sh CLI

## Help
`kafka-console-consumer.sh --help`

## Consume messages from the topic

### Not print keys
1. Start Console consumer: `kafka-console-consumer --bootstrap-server=localhost:9092 --topic=my-topic --from-beginning`
2. Stop Console consumer: Ctrl-C

### Print keys
1. Start Console consumer: 
```
kafka-console-consumer \
	--bootstrap-server=localhost:9092 \
	--topic=my-topic \
	--from-beginning \
	--property "print.key=true"
```
2. Stop Console consumer: Ctrl-C

### Consume the latest message only
```
kafka-console-consumer \
	--bootstrap-server=localhost:9092 \
	--topic=my-topic \
	--from-beginning \
	--max-messages 1"
```


## Set consumer group
`kafka-console-consumer.sh --bootstrap-server=$(broker-list.sh) --topic=my-topic --from-beginning --group my-group`


See yaal_examples

```
kafka-console-producer.sh --broker-list localhost:9092 --topic topic2
```

./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic topic2
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning

Where localhost:9092 is
- advertised.host.name = 172.18.0.1
- advertised.port = 9092
  

Available properties (source, usage: --property print.timestamp=true):
1.     print.timestamp - print the timestamp
2.     print.key - print the key
3.     print.value - print the value
4.     key.separator and line.separator
5.     key.deserializer and value.deserializer