# 020-integration-lambda-query-parameters

## Task
Create an **HTTP** API that invokes a Lambda Function which accepts **query** parameters.

## Steps
1. Create a Function
    1. Create:
        1. Type: `Author from scratch`
        2. Function name: `kata-f-integration-lambda-query-parameters`
        3. Runtime: `Python`
        4. Handler:
           ```python
            def lambda_handler(event, context):
                return { 'Event': event }
           ```
        5. Deploy
    2. Test the function
2. Creat an API:
    1. Create an HTTP API:
        1. API name: `kata-api-integration-lambda-query-parameters`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-f-integration-lambda-query-parameters`
    2. Rotes
        1. Method: `GET`
        2. Resource path: `/route1`
        3. Integration target: `kata-f-integration-lambda-query-parameters`
    3. Stages
        1. Stage name: `$default`
        2. Auto-deploy: enabled
3. Test using Stage `Invoke URI`:
    1. Invoke: `curl https://2mh9vqr8v8.execute-api.us-east-1.amazonaws.com/route1?aa=a1 | jq .`
    2. See `"queryStringParameters": { "aa": "a1" }`
    
## Cleanup
1. Delete the API
2. Delete the Function
3. Delete CloudWatch Log Group `/aws/lambda/kata-f-integration-lambda-query-parameters`

## History
- 2026-02-18 success
