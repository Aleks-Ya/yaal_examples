# Run Kafka cluster in Docker with fast-data-dev image

## Sources
Docker Hub: https://hub.docker.com/r/landoop/fast-data-dev

---------------------------------------------------------------------------------------------

## No SSL, No Authorization
### Run cluster
1. `docker run --rm --net=host -e DEBUG=1 lensesio/fast-data-dev`
2. Open http://localhost:3030/
3. Logs: `http://localhost:3030/logs/`

### Commands
Connect to the container by Bash: `docker run --rm -it --net=host --entrypoint bash lensesio/fast-data-dev`

#### kafka-topics
List topics: `kafka-topics --bootstrap-server localhost:9092 --list`
Describe topic: `kafka-topics --bootstrap-server localhost:9092 --describe --topic my-topic`
Create topic:
```
kafka-topics --bootstrap-server localhost:9092 \
    --create \
    --partitions 1 \
    --replication-factor 1 \
    --topic my-topic
```
Delete topic: `kafka-topics --bootstrap-server localhost:9092 --delete --topic my-topic`

#### kafka-configs
Describe topic config: `kafka-configs --zookeeper localhost:2181 --describe --entity-type topics --entity-name my-topic`
Change `max.message.bytes` topic property:
```
kafka-configs --zookeeper localhost:2181 \
    --alter --add-config max.message.bytes=256000 \
    --entity-type topics \
    --entity-name my-topic
```

#### kafka-console-producer
Produce to a topic: `kafka-console-producer --broker-list localhost:9092 --topic my-topic`

#### kafka-console-consumer
Consume from the beginning: `kafka-console-consumer --bootstrap-server localhost:9092 --from-beginning --topic my-topic`

---------------------------------------------------------------------------------------------

## With SSL, No Authorization
### Run cluster
1. `docker run --rm --net=host -e ENABLE_SSL=1 -e DEBUG=1 lensesio/fast-data-dev`
2. Logs: `http://localhost:3030/logs/`
3. Keys and certificates
    1. In UI: http://localhost:3030/certs/ (password for all: `fastdata`)
        2. Download:
            1. `wget http://localhost:3030/certs/client.jks`
            2. `wget http://localhost:3030/certs/clientA.jks`
            3. `wget http://localhost:3030/certs/clientB.jks`
            4. `wget http://localhost:3030/certs/truststore.jks`
    1. In container: 
        1. `/var/www/certs/clientA.jks`
        2. `/var/www/certs/clientB.jks`
        3. `/var/www/certs/client.jks`
        4. `/var/www/certs/truststore.jks`
    1. Print certs in JKS: `keytool -list -v -keystore clientA.jks -storepass fastdata`

### Commands
Connect to the container by Bash: `docker run --rm -it --net=host --entrypoint bash lensesio/fast-data-dev`
Download keystores:
```
wget http://localhost:3030/certs/clientA.jks -P /
wget http://localhost:3030/certs/clientB.jks -P /
wget http://localhost:3030/certs/client.jks -P /
wget http://localhost:3030/certs/truststore.jks -P /
```

#### kafka-topics
Create `command.properties`:
```
echo "security.protocol=SSL
ssl.keystore.location=client.jks
ssl.keystore.password=fastdata
ssl.key.password=fastdata
ssl.truststore.location=truststore.jks
ssl.truststore.password=fastdata" > /command.properties
```
List topics: `kafka-topics --bootstrap-server localhost:9093 --command-config /command.properties --list`
Create topic:
```
kafka-topics --bootstrap-server localhost:9093 --create \
    --command-config command.properties \
    --replication-factor 1 --partitions 1 --topic testSSL
```
Delete topic: `kafka-topics --bootstrap-server localhost:9093 --command-config command.properties --delete --topic testSSL`

#### kafka-console-producer
Produce to a topic (use property file):
```
echo "security.protocol=SSL
ssl.keystore.location=client.jks
ssl.keystore.password=fastdata
ssl.key.password=fastdata
ssl.truststore.location=truststore.jks
ssl.truststore.password=fastdata" > /producer.properties

kafka-console-producer --broker-list localhost:9093 --producer.config /producer.properties --topic my-topic
```
Produce to a topic (set properties in command):
```
kafka-console-producer --broker-list localhost:9093 \
    --producer-property security.protocol=SSL \
    --producer-property ssl.keystore.location=client.jks \
    --producer-property ssl.keystore.password=fastdata \
    --producer-property ssl.key.password=fastdata \
    --producer-property ssl.truststore.location=truststore.jks \
    --producer-property ssl.truststore.password=fastdata \
    --topic my-topic
```

#### kafka-console-consumer
Consume from the beginning (use property file):
```
echo "group.id=the-consumer-group_$RANDOM
security.protocol=SSL
ssl.keystore.location=client.jks
ssl.keystore.password=fastdata
ssl.key.password=fastdata
ssl.truststore.location=truststore.jks
ssl.truststore.password=fastdata" > /consumer.properties

kafka-console-consumer --bootstrap-server localhost:9093 --consumer.config /consumer.properties --from-beginning --topic my-topic
```
Consume from the beginning (set properties in command):
```
kafka-console-consumer --bootstrap-server localhost:9093 \
    --from-beginning \
    --consumer-property group.id=the-consumer-group_$RANDOM \
    --consumer-property security.protocol=SSL \
    --consumer-property ssl.keystore.location=client.jks \
    --consumer-property ssl.keystore.password=fastdata \
    --consumer-property ssl.key.password=fastdata \
    --consumer-property ssl.truststore.location=truststore.jks \
    --consumer-property ssl.truststore.password=fastdata \
    --topic my-topic
```

---------------------------------------------------------------------------------------------

## With SSL, With Authorization (NOT WORK)
### Run cluster
1. 
```
docker run --rm --net=host \
    -e ENABLE_SSL=1 \
    -e DEBUG=1 \
    -e KAFKA_AUTHORIZER_CLASS_NAME=kafka.security.authorizer.AclAuthorizer \
    lensesio/fast-data-dev
```
2. Logs: `http://localhost:3030/logs/`
