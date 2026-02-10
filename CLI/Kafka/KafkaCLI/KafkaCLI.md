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
	`source ~/pr/home/yaal_examples/CLI/Kafka/KafkaCLI/KafkaCLI.sh`
