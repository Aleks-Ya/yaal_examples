# kafka-topics.sh CLI

## Info
Help: `kafka-topics.sh --help`
Version: `kafka-topics.sh --version`

## Commands
### Create a topic
Simple: `kafka-topics.sh --bootstrap-server localhost:9092 --create --topic topic1`
With parameters:
```shell
kafka-topics.sh --bootstrap-server localhost:9092 \
	--create \
	--replication-factor 1 \
	--partitions 1 \
	--topic topic1
```
### List topics
`kafka-topics.sh --bootstrap-server localhost:9092 --list`
### Show replicas of a topic
`kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic topic1`
### Delete a topic
`kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic topic1`
### Describe a topic
Show: 1) partition number, 2) replication factor, 3) partition leader locations, 4) partition replica locations
`kafka-topics --bootstrap-server localhost:9092 --describe --topic my-topic`
### Change partition number
`kafka-topics.sh --bootstrap-server localhost:9092 --topic active_process --alter --partitions 3`

## Old versions (via ZooKeeper)
List topics: `kafka-topics.sh --zookeeper localhost:2181 --list`
Create topic: `kafka-topics.sh --zookeeper localhost:2181 --create --topic my-topic --partitions 1 --replication-factor 2`
Delete topic: `kafka-topics.sh --zookeeper localhost:2181 --delete --topic my-topic`
Describe topic: `kafka-topics.sh --zookeeper localhost:2181 --describe --topic my-topic`