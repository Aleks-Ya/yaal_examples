# Run Kafka cluster (single node)

Source: https://github.com/wurstmeister/kafka-docker

## Set the advertised host
`export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig docker0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}')`

## Run cluster
`docker-compose up -d`

## Scale cluster (optional)
`docker-compose up --scale kafka=2 -d`
Old: `docker-compose scale kafka=2`

## Check cluster state (optional)
`docker-compose ps`

## Set ZooKeeper coordinates
```
export ZK_HOST=$KAFKA_ADVERTISED_HOST_NAME
export ZK_PORT=2181
```

## Check Zookeeper (optional)
### Option 1: run separated container
`docker run -it --rm zookeeper zkCli.sh -server ${ZK_HOST}:${ZK_PORT}`
### Option 2: run default Kafka script
`docker exec -it kafkasinglecluster_kafka_1 bash -c '$KAFKA_HOME/bin/zookeeper-shell.sh $KAFKA_ZOOKEEPER_CONNECT'`

## Run Kafka Shell
```
docker run --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -e HOST_IP=${KAFKA_ADVERTISED_HOST_NAME} \
    -e ZK=${ZK_HOST}:${ZK_PORT} \
    -it wurstmeister/kafka /bin/bash
```

## Show app logs
`docker exec -it kafkasinglecluster_kafka_1 bash -c 'ls $KAFKA_HOME/logs'`

## Pause cluster (not loose data)
1. Stop: `docker-compose stop`
1. Start: `docker-compose start`
1. Ports are changed!

## Stop cluster
`docker-compose down`
