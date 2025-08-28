# 020-api-gateway-lambda-integration

## Task

Create an API that invokes a Lambda function.

Variants:

1. HTTP API
2. REST API

### Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `function-api-gateway`
    3. Runtime: `Python`
    4. Test the function
2. Build a REST API
    1. Integrations: Lambda `function-api-gateway`
    2. API name: `api-lambda-rest`
    3. Configure routes: method: `ANY`
    4. Test from Management Console
    4. Test from CLI: `curl https://ietdtgcao2.execute-api.us-east-1.amazonaws.com/function-api-gateway`
3. Build an HTTP API
    1. Add integration: `Lambda`
    2. Lambda function: `function-api-gateway`
    3. API name: `api-lambda-http`
    4. Rotes
        1. Method: `GET`
        2. Route `/function-api-gateway`
        3. Stage name: `$default`
    5. Test using `Invoke URI`: `curl https://2i2hx9bts1.execute-api.us-east-1.amazonaws.com/function-api-gateway`

Issue an ACM certificate 


### Cleanup
1. Delete the REST API
2. Delete the HTTP API
3. Delete the Lambda function
