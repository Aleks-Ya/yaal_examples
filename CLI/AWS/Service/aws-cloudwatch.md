# AWS CloudWatch CLI

## Commands
List Log Groups: `aws logs describe-log-groups`
List Log Streams in a Log Group: `aws logs describe-log-streams --log-group-name group1`
List metric streams: `aws cloudwatch list-metric-streams`
Delete a log group: `aws logs delete-log-group --log-group-name /aws/codebuild/kata1`
Delete a log stream: `aws logs delete-log-stream --log-group-name /aws/codebuild/kata1 --log-stream-name 42831698-e886-4e08-8b10-a8bcc86288d5`

## Filters
### CloudWatch Logs filters
By RequestID: `{ $.requestId = "702fc24f-9eae-4a64-95d1-120ec99e1a3d" }`

### Logs Insights filters
By RequestID:
```
fields @timestamp, requestId
 | filter requestId = '702fc24f-9eae-4a64-95d1-120ec99e1a3d'
```
