# 010-integration-lambda

## Task
Create an **HTTP** API that invokes a Lambda Function.

## Steps
1. Create a Function
    1. Create:
        1. Type: `Author from scratch`
        2. Function name: `kata-f-integration-lambda`
        3. Runtime: `Python`
    2. Test the Function
2. Build an API
    1. Create an HTTP API:
        1. API name: `kata-api-integration-lambda`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-f-integration-lambda`
    2. Rotes
        1. Method: `GET`
        2. Resource path: `/route1`
        3. Integration target: `kata-f-integration-lambda`
    3. Stages
        1. Stage name: `$default`
        2. Auto-deploy: enabled
    4. Test using Stage `Invoke URI`:
        `curl -i https://2f6bxpr740.execute-api.us-east-1.amazonaws.com/route1`

## Cleanup
1. Delete the API
2. Delete the Function
3. Delete CloudWatch Log Group `/aws/lambda/kata-f-integration-lambda`

## History
- 2026-02-18 success
