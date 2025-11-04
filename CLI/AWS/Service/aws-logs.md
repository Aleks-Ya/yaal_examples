# AWS CloudWatch Logs CLI

List all Log Groups: `aws logs list-log-groups`
List Log Groups by name: `aws logs list-log-groups --log-group-name-pattern s3-log-group`
Create a Log Group: `aws logs create-log-group --log-group-name s3-log-group`
Delete a Log Group: `aws logs delete-log-group --log-group-name s3-log-group`

List Log Streams in a Log Group: `aws logs describe-log-streams --log-group-name s3-log-group`
Search in a Log Group: `aws logs filter-log-events --log-group-name group1 --filter-pattern "Hello World"`

## Tail
Show events coming in a Log Stream: `aws logs tail --follow s3-log-group --log-stream-names 523633434047_CloudTrail_us-east-1`
Live tail for a Log Group: 
```shell
aws logs start-live-tail \
	--log-group-identifiers arn:aws:logs:us-east-1:523633434047:log-group:/aws/codebuild/kata-env-vars
```
