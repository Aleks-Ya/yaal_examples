# AWS SQS CLI

List queues: `aws sqs list-queues`
Get queue URL: `aws sqs get-queue-url --queue-name queue1`
Get queue ARN: `aws sqs get-queue-attributes --attribute-names QueueArn --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1`

Send a message to a queue: `aws sqs send-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1 --message-body "My message 1"`
Receive messages from a queue: `aws sqs receive-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1`

Create a queue: `aws sqs create-queue --queue-name queue1`
Delete a queue: `aws sqs delete-queue --queue-url https://sqs.ap-south-1.amazonaws.com/135808946124/queue1`

## Queue Access Policy
Set policy:
```shell 
aws sqs set-queue-attributes --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1 \
	--attributes Policy="'$(jq -c . queue-policy-minimal.json)'"
```