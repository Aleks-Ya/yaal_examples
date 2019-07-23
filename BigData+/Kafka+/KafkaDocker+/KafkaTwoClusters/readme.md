# Kafka (Two Clusters)
## Run
Source: https://github.com/wurstmeister/kafka-docker

### Set the advertised host
`export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig docker0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}')`

### Run cluster
`docker-compose up -d`

### Scale cluster (optional)
`docker-compose scale kafka=2`

### Check cluster state (optional)
`docker-compose ps`

### Set ZooKeeper coordinates
```
export ZK_HOST=$KAFKA_ADVERTISED_HOST_NAME
export ZK_PORT=2181
```

### Check Zookeeper (optional)
`docker run -it --rm zookeeper zkCli.sh -server ${ZK_HOST}:${ZK_PORT}`

### Kafka shell
#### Run Kafka Shell 1
```
docker run --rm -it \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -e HOST_IP=${KAFKA_ADVERTISED_HOST_NAME} \
    -e ZK=${ZK_HOST}:${ZK_PORT}/kafka1 \
    wurstmeister/kafka /bin/bash
```
#### Run Kafka Shell 2
```
docker run --rm -it \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -e HOST_IP=${KAFKA_ADVERTISED_HOST_NAME} \
    -e ZK=${ZK_HOST}:${ZK_PORT}/kafka2 \
    wurstmeister/kafka /bin/bash
```
#### Kafka Shell commands
##### Create topic
`$KAFKA_HOME/bin/kafka-topics.sh --create --topic my-topic --partitions 4 --zookeeper $ZK --replication-factor 1`
##### List topics
`$KAFKA_HOME/bin/kafka-topics.sh --zookeeper $ZK --list`
##### Describe topic
`$KAFKA_HOME/bin/kafka-topics.sh --zookeeper $ZK --describe --topic my-topic`
##### Add messages to the topic
`$KAFKA_HOME/bin/kafka-console-producer.sh --topic=my-topic --broker-list=$(broker-list.sh)`

Add messages from console. Press Ctrl-C to exit.

##### Consume messages from the topic
`$KAFKA_HOME/bin/kafka-console-consumer.sh --topic=my-topic --bootstrap-server=$(broker-list.sh) --from-beginning`

Press Ctrl-C to exit.

##### Mirror messages from one topic to another one
Mirroring is work permanently and copying new messages after they are added to source Kafka.

Docs: https://kafka.apache.org/documentation.html#basic_ops_mirror_maker

Source: Kafka1 cluster, topic `my-topic`. Destination: Kafka2 cluster

```
$KAFKA_HOME/bin/kafka-mirror-maker.sh \
    --consumer.config /root/mirror_consumer.properties \
    --producer.config /root/mirror_producer.properties \
    --whitelist my-topic
```

### Kafka Tool
Kafka Cluster Version: `2.1`
ZooKeeper Host: $KAFKA_ADVERTISED_HOST_NAME
ZooKeeper Port: `2181`
Chroot path: `/kafka1` (1st cluster), `/kafka2` (2nd cluster)

## Stop cluster
`docker-compose down`
