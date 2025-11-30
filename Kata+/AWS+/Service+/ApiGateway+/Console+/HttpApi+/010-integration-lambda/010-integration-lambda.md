# 010-integration-lambda

## Task
Create an HTTP API that invokes a Lambda function.

## Steps
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-f-integration-lambda`
    3. Runtime: `Python`
    4. Test the function
2. Build an API
    1. Create an HTTP API:
        1. API name: `kata-api-integration-lambda`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-f-integration-lambda`
    2. Rotes
        1. Method: `GET`
        2. Route `/kata-f-integration-lambda`
    3. Stages
        1. Stage name: `$default`
    4. Test using `Invoke URI`: `curl https://2f6bxpr740.execute-api.us-east-1.amazonaws.com/kata-f-integration-lambda`

## Cleanup
1. Delete the API
2. Delete the Function

## History
