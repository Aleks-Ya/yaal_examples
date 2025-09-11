# kafka-configs.sh CLI

## Describe
Display config for specific topic: `kafka-configs.sh --bootstrap-server localhost:9092 --describe --entity-type topics --entity-name my-topic`
Display configs for all topics: `kafka-configs.sh --bootstrap-server localhost:9092 --describe --entity-type topics`
Display config for specific topic: 

## Alter a topic config
```shell
kafka-configs.sh --bootstrap-server localhost:9092 \
    --alter --add-config max.message.bytes=256000 \
    --entity-type topics \
    --entity-name my-topic
```
