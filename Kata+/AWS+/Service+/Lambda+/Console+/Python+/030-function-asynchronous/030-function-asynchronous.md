# 030-function-asynchronous

## Task
Execute a Lambda Function asynchronously.

## Steps
1. Create a Queue
    1. Type: `Standard`
    2. Name: `kata-q-function-asynchronous`
2. Create a Function
    1. Type: `Author from scratch`
    2. Name: `kata-f-function-asynchronous`
    3. Runtime: `Python`
    4. Architecture: `x86_64`
    5. Permissions:
        1. Execution role: `Create a new role with basic Lambda permissions`
3. Configure the Function
    1. Code:
       ```python
       import time

       def lambda_handler(event, context):
           print(f"Start: {time.strftime('%H:%M:%S')}")
           time.sleep(10)
           print(f"Finish: {time.strftime('%H:%M:%S')}")
           return f"Hello from Lambda at {time.strftime('%H:%M:%S')}"
       ```
    2. Timeout: 15 sec 
    3. Deploy the Function
    4. Test the Function synchronously
       1. Create a synchronous test event
           1. Name: `te-sync`
           2. Invocation type: `Synchronous`
           3. Event JSON: `{}`
       2. Execute the test event 
    5. Add Destination
        1. Source: `Asynchronous invocation`
        2. Condition: `On success`
        3. Destination type: `SQS queue`
        4. Destination: `kata-q-function-asynchronous`
        5. Permissions: `Add required permissions`
    6. Test the Function asynchronously
       1. Create an asynchronous test event
           1. Name: `te-async`
           2. Invocation type: `Asynchronous`
           3. Event JSON: `{}`
       2. Execute the test event
       3. Verify the Queue

## Cleanup
1. Delete Function `kata-f-function-asynchronous`
2. Delete Role `kata-f-function-asynchronous-role-`
3. Delete Queue `kata-q-function-asynchronous`

## History
- 2025-12-29 success
