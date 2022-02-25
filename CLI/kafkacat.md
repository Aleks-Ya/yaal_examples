# kafkacat CLI

Does NOT support connecting via ZooKeeper

Info
Site: https://github.com/edenhill/kafkacat 
Install: apt-get install kafkacat
Examples: https://docs.eventador.io/examples/kafkacat 

Prepare
```
export BROKERS=172.17.0.1:32769


Read all messages from topic
kafkacat -b $BROKERS -t my_topic

```

