# 040-integration-lambda-body-parameters

## Task
Create an **HTTP** API that invokes a Lambda function which accepts **body** parameters.

## Steps
1. Create a Function
    1. Create:
        1. Type: `Author from scratch`
        2. Function name: `kata-f-integration-lambda-body-parameters`
        3. Runtime: `Python`
        4. Define handler:
           ```python
            import base64
            import json
    
            def lambda_handler(event, context):
                body_encoded: str = event.get('body', '')
                is_encoded: bool = event.get('isBase64Encoded', False)
                body_decoded: str = base64.b64decode(body_encoded).decode('utf-8') if is_encoded else body_encoded
                body_json: str = json.loads(body_decoded)
                user_id: str = body_json.get('userId', 'No user ID')
                return { 
                    'Event': event,
                    'Body': body_decoded,
                    'User ID': user_id
                }
           ```
        5. Deploy
    2. Test the Function
        1. Event JSON: `{ "body": "{\"userId\": \"john\"}" }`
2. Creat an API:
    1. Create an HTTP API:
        1. API name: `kata-api-integration-lambda-body-parameters`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-f-integration-lambda-body-parameters`
    2. Rotes
        1. Method: `POST`
        2. Resource path: `/body`
        3. Integration target: `kata-f-integration-lambda-body-parameters`
    3. Stages
        1. Stage name: `$default`
        2. Auto-deploy: enabled
3. Test using Stage `Invoke URI`:
    `curl -X POST https://ccfbyftlob.execute-api.us-east-1.amazonaws.com/body -d '{"userId":"123"}' | jq .`
    
## Cleanup
1. Delete the API
2. Delete the Function
3. Delete CloudWatch Log Group `/aws/lambda/kata-f-integration-lambda-body-parameters`

## History
- 2026-02-18 success
