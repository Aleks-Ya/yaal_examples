# 02-api-gateway-lambda-integration

## Task

Create an API that invokes a Lambda function.

Variants:

1. HTTP API
2. REST API

## HTTP API variant

### Setup

1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `function-1`
    3. Runtime: `Python`
    4. Test the function
2. Create an API
    1. Integrations: Lambda `function-1`
    2. API name: `api-1`
    3. Configure routes: method: `ANY`
    4. Test: `curl https://ietdtgcao2.execute-api.us-east-1.amazonaws.com/function-1`
2. Build an HTTP API
    1. Add integration: `Lambda`
    2. Lambda function: `function-1`
    3. API name: `api-1`
    4. Rotes
        1. Method: `GET`
        2. Route `/function-1`
        3. Stage name: `$default`
3. Test using `Invoke URI`: `curl https://2i2hx9bts1.execute-api.us-east-1.amazonaws.com/function-1`

Issue an ACM certificate 


### Cleanup

1. Delete the API
2. Delete the Lambda function
