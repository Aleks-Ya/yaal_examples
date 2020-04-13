# kafka-topics CLI

Location: `$KAFKA_HOME/bin/kafka-topics.sh`

## Help
`kafka-topics.sh --help`

## Show version
`kafka-topics.sh --version`

## List topics
`kafka-topics.sh --bootstrap-server $(broker-list.sh) --list`

## Create topic
```
kafka-topics.sh \
	--bootstrap-server $(broker-list.sh) \
	--create \
	--topic my-topic \
	--partitions 3 \
	--replication-factor 2
```

## Delete topic
`kafka-topics.sh --bootstrap-server $(broker-list.sh) --delete --topic my-topic`

## Describe topic
Show: 1) partition number, 2) replication factor, 3) partition leader locations, 4) partition replica locations
`kafka-topics.sh --bootstrap-server $(broker-list.sh) --describe --topic my-topic`

## Change partition number
`kafka-topics.sh --bootstrap-server $(broker-list.sh) --topic active_process --alter --partitions 3`

## Old versions (via ZooKeeper)
List topics: `kafka-topics.sh --zookeeper $ZK --list`
Create topic: `kafka-topics.sh --zookeeper $ZK --create --topic my-topic --partitions 3 --replication-factor 2`
Delete topic: `kafka-topics.sh --zookeeper $ZK --delete --topic my-topic`
Describe topic: `kafka-topics.sh --zookeeper $ZK --describe --topic my-topic`