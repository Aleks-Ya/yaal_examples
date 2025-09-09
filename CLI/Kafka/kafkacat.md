# KCat (KafkaCat) CLI

Does NOT support connecting via ZooKeeper.

## Info
Site: https://github.com/edenhill/kcat
Examples: https://docs.eventador.io/examples/kafkacat 

## Install
1. Install package: `sudo apt install -y kafkacat`
2. Configure terminal:
	1. Set alias: `alias kcat="kcat -b localhost:9092"`
	2. Verify alias: `kcat -L`

## Commands
Help: `kcat -h`
Show metadata (brokers, topics, partitions): `kcat -L`
Read all messages from topic: `kcat -t topic1`

## Run from Docker
DockerHub: https://hub.docker.com/r/confluentinc/cp-kafkacat
Show metadata: `docker run --rm -it --network=host confluentinc/cp-kafkacat kafkacat -b localhost:9092 -L`
