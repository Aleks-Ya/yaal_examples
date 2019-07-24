# Run Kafka cluster (single node)

Source: https://github.com/wurstmeister/kafka-docker

## Set the advertised host
`export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig docker0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}')`

## Run cluster
`docker-compose up -d`

## Scale cluster (optional)
`docker-compose scale kafka=2`

## Check cluster state (optional)
`docker-compose ps`

## Set ZooKeeper coordinates
```
export ZK_HOST=$KAFKA_ADVERTISED_HOST_NAME
export ZK_PORT=2181
```

## Check Zookeeper (optional)
`docker run -it --rm zookeeper zkCli.sh -server ${ZK_HOST}:${ZK_PORT}`

## Run Kafka Shell
`docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -e HOST_IP=${KAFKA_ADVERTISED_HOST_NAME} -e ZK=${ZK_HOST}:${ZK_PORT} -it wurstmeister/kafka /bin/bash`

## Stop cluster
`docker-compose down`
