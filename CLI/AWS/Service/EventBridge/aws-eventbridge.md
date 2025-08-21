# EventBridge AWS CLI

## Events
List rules: `aws events list-rules`
Put an Event to the default bus: `aws events put-events --entries '[{"Source":"me","DetailType":"type1","Detail":"{\"key1\":\"value1\"}"}]'`
Put an Event to given Event Bus: `aws events put-events --entries '[{"EventBusName":"bus1","Source":"me","DetailType":"type1","Detail":"{ \"key1\": \"value1\"}"}]'`
Put Events from a file: `aws events put-events --entries file://entries.json`



## Scheduler
List schedules: `aws scheduler list-schedules`
