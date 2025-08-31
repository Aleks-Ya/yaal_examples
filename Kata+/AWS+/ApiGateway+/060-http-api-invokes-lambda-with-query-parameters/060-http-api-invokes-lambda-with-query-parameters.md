# 060-http-api-invokes-lambda-with-query-parameters

## Task

Create an **HTTP** API that invokes a Lambda function which accepts **query** parameters.

### Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-query-parameters-http-api-function`
    3. Runtime: `Python`
    4. Define handler:
       ```python
        def lambda_handler(event, context):
            return { 'Event': event }
       ```
    5. Test the function
2. Creat an API:
    1. Create an HTTP API:
        1. API name: `kata-query-parameters-lambda-http-api`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-query-parameters-http-api-function`
    2. Rotes
        1. Method: `GET`
        2. Route `/kata-query-parameters-http-api-function`
    3. Stages
        1. Stage name: `$default`
3. Deploy the API:
    1. New stage: `$default`
    2. Test from CLI: `curl https://2mh9vqr8v8.execute-api.us-east-1.amazonaws.com/kata-query-parameters-http-api-function?aa=a1 | jq .`
    
### Cleanup
1. Delete the API
2. Delete the Function
