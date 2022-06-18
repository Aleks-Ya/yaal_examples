# kafka-consumer-groups CLI

## Info
Help: `kafka-consumer-groups.sh --help`
Version: `kafka-consumer-groups.sh --bootstrap-server abc --version`

## Display all consumer groups
`kafka-consumer-groups --bootstrap-server=localhost:9092 --list`

## Describe a consumer group
All groups: `kafka-consumer-groups --bootstrap-server=localhost:9092 --describe --all-groups`
Specific group: `kafka-consumer-groups --bootstrap-server=localhost:9092 --describe --group my-group`

Shows: 
1. active (connected) consumers
2. consumer offsets
3. consumer lags
4. topic offsets

## Delete consumer group
`kafka-consumer-groups --bootstrap-server=localhost:9092 --delete --group my-group`
