# kafka-configs CLI

## Display configs for all topics
`kafka-configs.sh --zookeeper $ZK --describe --entity-type topics`

## Display config for specific topic
`kafka-configs.sh --zookeeper $ZK --describe --entity-type topics --entity-name my-topic`