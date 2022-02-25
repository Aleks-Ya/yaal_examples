# kafka-console-consumer.sh CLI

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
   
