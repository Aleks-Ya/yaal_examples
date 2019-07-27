# kafka-topics CLI

Location: `$KAFKA_HOME/bin/kafka-topics.sh`

## List topics
`kafka-topics.sh --zookeeper $ZK --list`

## Create topic
`kafka-topics.sh --zookeeper $ZK --create --topic my-topic --partitions 3 --replication-factor 2`

## Delete topic
`kafka-topics.sh --zookeeper $ZK --delete --topic my-topic`

## Describe topic 
Show: 1) partition number, 2) replication factor
`kafka-topics.sh --zookeeper $ZK --describe --topic my-topic`