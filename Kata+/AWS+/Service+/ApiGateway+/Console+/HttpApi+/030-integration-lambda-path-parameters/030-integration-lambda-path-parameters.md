# 030-integration-lambda-path-parameters

## Task
Create an **HTTP** API that invokes a Lambda Function which accepts **path** parameters.

## Steps
1. Create a Function
    1. Create:
        1. Type: `Author from scratch`
        2. Function name: `kata-f-integration-lambda-path-parameters`
        3. Runtime: `Python`
        4. Handler:
           ```python
            def lambda_handler(event, context):
                path_parameters: dict[str, str] = event.get('pathParameters', {})
                user_id: str = path_parameters.get('userId', 'No user ID')
                return { 
                    'Event': event,
                    'Path Parameters': path_parameters,
                    'User ID': user_id
                }
           ```
        5. Deploy
    2. Test the Function
2. Create an API:
    1. Create an HTTP API:
        1. API name: `kata-api-integration-lambda-path-parameters`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-f-integration-lambda-path-parameters`
    2. Rotes
        1. Method: `GET`
        2. Resource path: `/users/{userId}`
        3. Integrations target: `kata-f-integration-lambda-path-parameters`
    3. Stages
        1. Stage name: `$default`
        2. Auto-deploy: enabled
3. Test using Stage `Invoke URI`:
    `curl https://7m98b362b9.execute-api.us-east-1.amazonaws.com/users/1234 | jq .`
    
## Cleanup
1. Delete the API
2. Delete the Function
3. Delete CloudWatch Log Group `/aws/lambda/kata-f-integration-lambda-path-parameters`

## History
- 2026-02-18 success
