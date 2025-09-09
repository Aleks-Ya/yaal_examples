# KafkaCLI

## Install
1. Download ZIP from https://kafka.apache.org/downloads
2. Unpack ZIP to `~/installed/kafka/kafka_2.13-4.1.0`
3. Set env vars with `subl ~/.bashrc`:
	1. Add `export KAFKA_HOME=/home/aleks/installed/kafka/kafka_2.13-4.1.0`
	2. Add `export PATH=$PATH:$KAFKA_HOME/bin`
4. Verify: `kafka-topics.sh --version`

## Aliases
Create aliases in terminal to work with `BigData+/Kafka+/KafkaDocker+/official/OfficialKafkaImage.md`:
```shell
alias kafka-topics="kafka-topics.sh --bootstrap-server localhost:9092"
alias kafka-console-producer="kafka-console-producer.sh --bootstrap-server localhost:9092"
alias kafka-console-consumer="kafka-console-consumer.sh --bootstrap-server localhost:9092"
alias kafka-consumer-groups="kafka-consumer-groups.sh --bootstrap-server localhost:9092"
```