# 120-function-error-json

## Task
Create a function that fails and return an error as a JSON object.

## Setup
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `kata-function-error-json-function`
	3. Runtime: `Python`
	4. Architecture: `x86_64`
	5. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
	6. Additional configurations
		1. Function URL: 
			1. Enabled: true
			2. Auth type: `NONE`
2. Define handler:
```python
import json

def lambda_handler(event, context):
    details: dict[str, str] = { "details": "Invalid parameter A" }
    return {
        'statusCode': 400,
        'body': json.dumps(details)
    }
```
3. Deploy the function
4. Test
	1. Invoke by a test event
	2. Ivoke with CLI: 
		1. Invoke: `aws lambda invoke --function-name kata-function-error-json-function /tmp/function-error-json.json`
		2. Verify body: `jq . /tmp/function-error-json.json`
	3. Invoke via function URL: `curl -i https://uufbmucj7nih6p5l32o4cjhcay0qaire.lambda-url.us-east-1.on.aws`

## Cleanup
1. Delete function's execution role
2. Delete function `kata-function-error-json-function`
