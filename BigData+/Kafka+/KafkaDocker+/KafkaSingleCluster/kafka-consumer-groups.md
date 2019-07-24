# kafka-consumer-groups CLI

## Display all consumer groups
`kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --list`

## Describe a consumer group
`kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --describe --group my-group`
Shows: 
1. active (connected) consumers
2. consumer offsets
3. consumer lags
4. topic offsets
