# 030-dead-letter-queue

## Task

Create a Queue #1 intended to be a DLQ. Create another Queue #2 which uses Queue #1 as DLQ.
Simulate error during message consuming to trigger sending the error message to the DLQ.

## Setup

1. Create the DLQ.
    1. Type: `Standard`
    2. Name: `dlq-1`
    3. Redrive allow policy
        1. Enabled
        2. Redrive permission: `Allow all`
2. Create the Queue
    1. Type: `Standard`
    2. Name: `queue-1`
    3. Dead-letter queue
        1. Enabled
        2. Choose queue: `dlq-1`
        3. Maximum receives: `1`
3. Create a Lambda Function
    1. Create a Function
        1. Type: `Author from scratch`
        2. Name: `function-1`
        3. Runtime: `Python`
        4. Permissions
            1. Execution role: `Create a new role from AWS policy templates`
            2. Role name: `lambda-sqs-poll-role`
            3. Policy templates: `Amazon SQS poller permissions`
    2. Configure the Function
        1. Set timeout to 1 min
        2. Code
           ```python
           import boto3

           def lambda_handler(event, context):
               sqs = boto3.client('sqs')
               queue_url = 'https://sqs.us-east-1.amazonaws.com/523633434047/queue-1'
               response = sqs.receive_message(
                  QueueUrl=queue_url,
                  MaxNumberOfMessages=1,
                  WaitTimeSeconds=10
               )
               messages = response.get('Messages', [])
               for message in messages:
                   print(f'Message was received: {message}')
           ```
    3. Deploy
4. Test
    1. Produce a message to the Queue using AWS Console
    2. Run the Function
    3. Wait 30 sec until the Visibility Timeout ends
    4. Check that message was sent to `dlq-1` queue

## Cleanup

1. Delete queues: `queue-1` and `dlq-1`
2. Delete Function: `function-1`
3. Delete Role: `lambda-sqs-poll-role`
