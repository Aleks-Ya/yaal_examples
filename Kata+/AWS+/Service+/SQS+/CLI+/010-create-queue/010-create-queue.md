# 010-create-queue

## Task
Create an SQS Queue.

## Steps
1. Open a new terminal
2. Set environment variables
	```shell
	set -x
	export Q_NAME=kata-q-create-queue
	```
3. Create a Queue: `aws sqs create-queue --queue-name $Q_NAME`
4. Get Queue URL: `export Q_ARN=$(aws sqs get-queue-url --output text --queue-name $Q_NAME)`
5. Send a message to a queue: `aws sqs send-message --queue-url $Q_ARN --message-body "Message 1"`
6. Receive the message: `aws sqs receive-message --queue-url $Q_ARN`

## Cleanup
1. Delete the Queue: `aws sqs delete-queue --queue-url $Q_ARN`
2. Close the terminal

## History
- 2025-12-09 success