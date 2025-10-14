# 020-integration-lambda

## Task
Status: ?
Create a REST API that invokes a Lambda function.

## Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `rest-api-function`
    3. Runtime: `Python`
    4. Test the function
2. Build an API:
    1. Integrations: Lambda `rest-api-function`
    2. API name: `lambda-rest-api`
    3. Configure routes: method: `ANY`
    4. Test from Management Console
    5. Test from CLI: `curl https://ietdtgcao2.execute-api.us-east-1.amazonaws.com/rest-api-function`
    
## Cleanup
1. Delete the API
2. Delete the Lambda function

## History
