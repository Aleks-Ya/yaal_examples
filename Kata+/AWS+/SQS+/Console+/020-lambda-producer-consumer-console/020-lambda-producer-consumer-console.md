# 020-lambda-producer-consumer-console

## Task

Create two Python Lambda Functions: produces a message to a Queue and consumes the message.

## Setup

1. Create a Queue
    1. Type: `Standard`
    2. Name: `queue-1`
2. Create a Producer Lambda Function
    1. Create a Function
        1. Type: `Author from scratch`
        2. Name: `producer-1`
        3. Runtime: `Python`
    2. Configure the Function
        1. Create an inline policy for the Execution Role
            1. Policy:
               ```json
               {
                   "Version": "2012-10-17",
                   "Statement": [
                       {
                           "Sid": "VisualEditor0",
                           "Effect": "Allow",
                           "Action": "sqs:SendMessage",
                           "Resource": "arn:aws:sqs:us-east-1:523633434047:queue-1"
                       }
                   ]
               }
               ```
            2. Policy name: `sqs-send-message`
        2. Set code
           ```python
           import boto3
   
           def lambda_handler(event, context):
               sqs = boto3.client('sqs')
               queue_url = 'https://sqs.us-east-1.amazonaws.com/523633434047/queue-1'
               message_body = 'Message 1'
               response = sqs.send_message(
                   QueueUrl=queue_url,
                   MessageBody=message_body
               )
               print(f'Message was sent: {message_body}')
               return response
           ```
    3. Deploy the Function
    4. Test the Function
3. Create a Consumer Lambda Function
    1. Create a Function
        2. Type: `Author from scratch`
        2. Name: `consumer-1`
        3. Runtime: `Python`
    2. Create the function
        1. Set timeout to 1 min
        2. Create an inline policy to the Execution Role
            1. Policy:
               ```json
               {
                   "Version": "2012-10-17",
                   "Statement": [
                       {
                           "Sid": "VisualEditor0",
                           "Effect": "Allow",
                           "Action": [
                               "sqs:DeleteMessage",
                               "sqs:ReceiveMessage"
                           ],
                           "Resource": "arn:aws:sqs:us-east-1:523633434047:queue-1"
                       }
                   ]
               }
               ```
            2. Policy name: `sqs-receive-message`
        3. Set code
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
                   sqs.delete_message(
                       QueueUrl=queue_url,
                       ReceiptHandle=message['ReceiptHandle']
                   )
                   print(f'Message was deleted: {message}')
           ```
    3. Test the Function

## Cleanup

1. Delete Lambda Functions: `consumer-1` and `producer-1`
2. Delete Execution Roles: `producer-1-role-` and `consumer-1-role-`
3. Delete Queue: `queue-1`
