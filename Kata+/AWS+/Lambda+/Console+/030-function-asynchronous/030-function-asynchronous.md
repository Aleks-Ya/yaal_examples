# 030-function-asynchronous

## Task
Status: ?
Execute a Lambda Function asynchronously.

## Setup
1. Create a Queue
    1. Type: `Standard`
    2. Name: `queue-1`
2. Create a Function
    1. Type: `Author from scratch`
    2. Name: `function-1`
    3. Runtime: `Python`
3. Configure the Function
    1. Timeout: 15 sec
    2. Code:
       ```python
       import time

       def lambda_handler(event, context):
           print(f"Start: {time.strftime('%H:%M:%S')}")
           time.sleep(10)
           print(f"Finish: {time.strftime('%H:%M:%S')}")
           return f"Hello from Lambda at {time.strftime('%H:%M:%S')}"
       ```
    3. Deploy the Function
    4. Test the Function
    5. Add Destination
        1. Source: `Asynchronous invocation`
        2. Condition: `On success`
        3. Destination type: `SQS queue`
        4. Destination: `queue-1`
        5. Permissions: `Add required permissions`
4. Test
    1. Invoke the Function: `aws lambda invoke --function-name function-1 --invocation-type Event /tmp/response.json`
    2. Check message in the Queue using AWS Console

## Cleanup
1. Delete Function
2. Delete Role `function-1-role-`
3. Delete Queue

## History
