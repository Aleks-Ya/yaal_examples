# kafka-consumer-groups CLI

## Display all consumer groups
`kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --list`

## Describe a consumer group
All groups: `kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --describe --all-groups`
Specific group: `kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --describe --group my-group`

Shows: 
1. active (connected) consumers
2. consumer offsets
3. consumer lags
4. topic offsets

## Delete consumer group
`kafka-consumer-groups.sh --bootstrap-server=$(broker-list.sh) --delete --group my-group`
