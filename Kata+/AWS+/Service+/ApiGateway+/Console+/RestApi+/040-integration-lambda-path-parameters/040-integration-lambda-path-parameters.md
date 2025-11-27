# 040-integration-lambda-path-parameters

## Task
Status: ?
Create a **REST** API that invokes a Lambda function which accepts **path** parameters.

## Steps
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-path-parameters-rest-api-function`
    3. Runtime: `Python`
    4. Define handler:
       ```python
        def lambda_handler(event, context):
            return { 'Event': event }
       ```
    5. Test the function
2. Creat an API:
    1. Build a REST API:
        1. API name: `kata-path-parameters-lambda-rest-api`
    2. Create "users" resource
        1. Resource path: `/`
        2. Resource name: `users`
    3. Create "userId" resource
        1. Resource path: `/users/`
        2. Resource name: `{userId}`
    2. Create a method in resource "/users/{userId}":
        1. Method type: `ANY`
        2. Integration type: Lambda function
        3. Lambda function: `kata-path-parameters-rest-api-function`
    3. Configure the method:
        1. Integration request: 
            1. Mapping template:
                1. Content type: `application/json`
                2. Template body: `{"userIdentifier":"$input.params('userId')"}`
    4. Test from "Test" tab on the method page:
        1. Method type: `GET`
        2. Path "userId": `123`
6. Deploy the API:
    1. New stage: `stage1`
    2. Test from CLI: `curl https://w0z68flcf2.execute-api.us-east-1.amazonaws.com/stage1/users/345`
    
## Cleanup
1. Delete the API
2. Delete the Function

## History
