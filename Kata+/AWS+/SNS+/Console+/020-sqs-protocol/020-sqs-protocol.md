# 020-sqs-protocol

## Task
Status: success
Create a Topic that delivers messages to an SQS queue.

## Setup
1. Create Queue
	1. Type: `Standard`
	2. Name: `kata-queue-sqs-protocol`
2. Create Topic
	1. Type: `Standard`
	2. Name: `kata-sqs-protocol-topic`
3. Create Subscription
	1. Protocol: `Amazon SQS`
	2. Endpoint: queue `kata-queue-sqs-protocol`
4. Test Topic
	1. Publish a test message
	2. See the message in the queue

## Cleanup
1. Delete Subscription
2. Delete Topic
3. Delete Queue

## History
- 2025-10-12 success
