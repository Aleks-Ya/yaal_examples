# CLIs

## Prepare
1. Run Kafka Shell (`README.md`)
2. Create aliases:
```
alias ktopics='kafka-topics.sh --bootstrap-server $(broker-list.sh) '
alias kproducer='kafka-console-producer.sh --broker-list=$(broker-list.sh) '
alias kconsumer='kafka-console-consumer.sh --bootstrap-server=$(broker-list.sh) '
alias kconfigs='kafka-configs.sh --bootstrap-server $(broker-list.sh) '
alias kgroups='kafka-consumer-groups.sh --bootstrap-server $(broker-list.sh) '
alias kacls='kafka-acls.sh --bootstrap-server $(broker-list.sh) '
```
3. Execute commands

## Usage examples
Create topic: `ktopics --create --topic my-topic`
List topics: `ktopics --list`
Display config for topics: `kconfigs --describe --entity-type topics`
Produce to topic: `kproducer --topic=my-topic`
Consume topic: `kconsumer --topic=my-topic --from-beginning`
List consumer groups: `kgroups --list`
List ACLs: `kacls --list`
