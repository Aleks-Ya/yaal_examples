# Kafka
## Run

### Clone repo
```
git clone git@github.com:wurstmeister/kafka-docker.git
cd kafka-docker
git ch 1.1.0
```

### Set the advertised host
`export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig docker0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}')`

### Set KAFKA_ADVERTISED_HOST_NAME in docker-compose.yml
`rpl 192.168.99.100 ${KAFKA_ADVERTISED_HOST_NAME} docker-compose.yml`

### Run cluster
`docker-compose up -d`

### Scale cluster
`docker-compose scale kafka=2`

### Check cluster state
`docker-compose ps`

### Set ZooKeeper coordinates
```
export ZK_HOST=$KAFKA_ADVERTISED_HOST_NAME
export ZK_PORT=2181
```

### Check Zookeeper (optional)
`docker run -it --rm zookeeper zkCli.sh -server ${ZK_HOST}:${ZK_PORT}`

### Kafka shell
#### Run Kafka shell
`./start-kafka-shell.sh ${KAFKA_ADVERTISED_HOST_NAME} ${ZK_HOST}:${ZK_PORT}`
#### Create topic
`$KAFKA_HOME/bin/kafka-topics.sh --create --topic topic --partitions 4 --zookeeper $ZK --replication-factor 2`
#### Describe topic
`$KAFKA_HOME/bin/kafka-topics.sh --describe --topic topic --zookeeper $ZK`
#### Add messages to the topic
`$KAFKA_HOME/bin/kafka-console-producer.sh --topic=topic --broker-list=$(broker-list.sh)`

Add messages from console. Press Ctrl-C to exit.

#### Consume messages from the topic
`$KAFKA_HOME/bin/kafka-console-consumer.sh --topic=topic --bootstrap-server=$(broker-list.sh) --from-beginning`

Press Ctrl-C to exit.

## Stop cluster
`docker-compose down`
