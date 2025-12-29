# 040-body-parameters

## Task
Extract parameters from HTTP request body within a Lambda Function.

## Steps
1. Create a Function:
    1. Template: `Author from scratch`
    2. Function name: `kata-f-body-parameters`
    3. Runtime: `Python`
    4. Architecture: `x86_64`
    5. Permissions:
        1. Execution role: `Create a new role with basic Lambda permissions`
2. Configure the Function
	1. Define handler:
		```python
		import json
		from typing import Optional
		
		def lambda_handler(event, context):
			print(f"Event: '{event}'")
			params: dict = json.loads(event['body'])
			print(f"Params: '{params}'")
			name_param: Optional[str] = params.get('name')
			age_param: Optional[int] = params.get('age')
			absent_param: Optional[str] = params.get('absent')
			return {
				'Event': event,
				'Name Parameter': name_param,
				'Age Parameter': age_param,
				'Absent Parameter': absent_param
			}
		```
	2. Deploy the function
	3. Enable function URL:
    	1. Auth type: `NONE`
3. Test by a test event
	1. Create a test event
		1. Name: `te1`
		2. Invocation type: `Synchronous`
		3. Event JSON: `{ "body": "{\"name\": \"John\", \"age\": 30}" }`
	2. Invoke the test event
4. Test by AWS CLI:
	```shell
	curl -s -X POST -H "Content-Type: application/json" \
		https://jgf322jx4hta2lbazt3cihhwtu0txrbg.lambda-url.us-east-1.on.aws/ \
		-d '{"name":"John","age":30}' | jq .
	```

## Cleanup
1. Delete function `kata-f-body-parameters`
2. Delete Role `kata-f-body-parameters-role-`

## History
- 2025-12-29 success
