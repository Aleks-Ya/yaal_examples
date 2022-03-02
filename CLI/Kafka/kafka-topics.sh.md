# kafka-topics.sh CLI

Create a topic:
```
#By bootstrap server
kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 1 --topic fibonacc
#By Zookeeper
kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic fibonacc
```
Show topic list:
```
#By bootstrap server
kafka-topics.sh --bootstrap-server localhost:9092 --list
#By Zookeeper
kafka-topics.sh --zookeeper localhost:2181 --list
```
Show replicas of a topic:
```
#By bootstrap server
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic fibonacc
#By Zookeeper
kafka-topics.sh --zookeeper localhost:2181 --describe --topic fibonacc
```
Delete a topic:
```
#By bootstrap server
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic fibonacc
#By Zookeeper
kafka-topics.sh --zookeeper localhost:2181 --delete --topic fibonacc
```
Describe a topic:
```
kafka-topics --bootstrap-server localhost:9092 --describe --topic my-topic
```
