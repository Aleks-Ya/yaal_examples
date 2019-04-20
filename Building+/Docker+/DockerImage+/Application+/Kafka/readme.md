# Kafka
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
#### Run Kafka Shell
`docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -e HOST_IP=${KAFKA_ADVERTISED_HOST_NAME} -e ZK=${ZK_HOST}:${ZK_PORT} -it wurstmeister/kafka /bin/bash`
#### Kafka Shell commands
##### Create topic
`$KAFKA_HOME/bin/kafka-topics.sh --create --topic my_topic --partitions 4 --zookeeper $ZK --replication-factor 1`
##### Describe topic
`$KAFKA_HOME/bin/kafka-topics.sh --describe --topic my_topic --zookeeper $ZK`
##### Add messages to the topic
`$KAFKA_HOME/bin/kafka-console-producer.sh --topic=my_topic --broker-list=$(broker-list.sh)`

Add messages from console. Press Ctrl-C to exit.

##### Consume messages from the topic
`$KAFKA_HOME/bin/kafka-console-consumer.sh --topic=my_topic --bootstrap-server=$(broker-list.sh) --from-beginning`

Press Ctrl-C to exit.

### Kafka Tool
Kafka Cluster Version: `2.1`
ZooKeeper Host: $KAFKA_ADVERTISED_HOST_NAME
ZooKeeper Port: `2181`

## Stop cluster
`docker-compose down`
