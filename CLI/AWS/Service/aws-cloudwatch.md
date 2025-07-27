# AWS CloudWatch CLI

## Commands
List metric streams: `aws cloudwatch list-metric-streams`

## Filters
### CloudWatch Logs filters
By RequestID: `{ $.requestId = "702fc24f-9eae-4a64-95d1-120ec99e1a3d" }`

### Logs Insights filters
By RequestID:
```
fields @timestamp, requestId
 | filter requestId = '702fc24f-9eae-4a64-95d1-120ec99e1a3d'
```
