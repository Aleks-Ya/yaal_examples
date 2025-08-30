# 030-http-api-invokes-lambda

## Task

Create an HTTP API that invokes a Lambda function.

### Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `http-api-function`
    3. Runtime: `Python`
    4. Test the function
2. Build an HTTP API
    1. Add integration: `Lambda`
    2. Lambda function: `http-api-function`
    3. API name: `lambda-http-api`
    4. Rotes
        1. Method: `GET`
        2. Route `/http-api-function`
        3. Stage name: `$default`
    5. Test using `Invoke URI`: `curl https://2i2hx9bts1.execute-api.us-east-1.amazonaws.com/http-api-function`

### Cleanup
1. Delete the API
2. Delete the Lambda function
