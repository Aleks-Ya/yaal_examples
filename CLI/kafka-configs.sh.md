# kafka-configs.sh CLI

Describe topic config:
```
kafka-configs --zookeeper localhost:2181 --describe --entity-type topics --entity-name my-topic
```
Alter a topic config:
```
kafka-configs --zookeeper localhost:2181 \
    --alter --add-config max.message.bytes=256000 \
    --entity-type topics \
    --entity-name my-topic
```
