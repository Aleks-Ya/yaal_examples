# 030-integration-lambda-path-parameters

## Task
Status: ?
Create an **HTTP** API that invokes a Lambda function which accepts **path** parameters.

## Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-path-parameters-http-api-function`
    3. Runtime: `Python`
    4. Define handler:
       ```python
        def lambda_handler(event, context):
            path_parameters = event.get('pathParameters', {})
            user_id = path_parameters.get('userId', 'No user ID')
            return { 
                'Event': event,
                'Path Parameters': path_parameters,
                'User ID': user_id
                }
       ```
    5. Test the function
2. Creat an API:
    1. Create an HTTP API:
        1. API name: `kata-path-parameters-lambda-http-api`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-path-parameters-http-api-function`
    2. Rotes
        1. Method: `GET`
        2. Resource path: `/users/{userId}`
        2. Route `/kata-path-parameters-http-api-function`
    3. Stages
        1. Stage name: `$default`
3. Deploy the API:
    1. New stage: `$default`
    2. Test from CLI: `curl https://7m98b362b9.execute-api.us-east-1.amazonaws.com/users2/1234 | jq .`
    
## Cleanup
1. Delete the API
2. Delete the Function

## History
