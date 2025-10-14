# 040-integration-lambda-body-parameters

## Task
Status: ?
Create an **HTTP** API that invokes a Lambda function which accepts **body** parameters.

## Setup
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-body-parameters-http-api-function`
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
    5. Test the function
2. Creat an API:
    1. Create an HTTP API:
        1. API name: `kata-body-parameters-lambda-http-api`
        2. Add integration: 
            1. Type: `Lambda`
            2. Lambda function: `kata-body-parameters-http-api-function`
    2. Rotes
        1. Method: `ANY`
        2. Resource path: `/body`
        2. Route `/kata-body-parameters-http-api-function`
    3. Stages
        1. Stage name: `$default`
3. Deploy the API:
    1. New stage: `$default`
    2. Test from CLI: `curl -X POST https://gil2t36oxd.execute-api.us-east-1.amazonaws.com/body -d '{"userId":"123"}' | jq .`
    
## Cleanup
1. Delete the API
2. Delete the Function
3. Delete CloudWatch Log Group `/aws/lambda/kata-body-parameters-http-api-function`

## History
