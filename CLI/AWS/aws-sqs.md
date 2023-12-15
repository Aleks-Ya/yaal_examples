# AWS SQS CLI

List queues: `aws sqs list-queues`
Send a message to a queue: `aws sqs send-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue-1 --message-body "My message 1"`
Receive messages from a queue: `aws sqs receive-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue-1`
