# 040-body-parameters

## Task
Status: ?
Extract parameters from HTTP request body within a Lambda Function.

## Steps
1. Create a function:
    1. Template: `Author from scratch`
    2. Function name: `kata-body-parameters-function`
    3. Runtime: `Python`
    4. Enable function URL:
        1. Auth type: `NONE`
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
3. Test
    1. Send request to Function URL:
	```shell
	curl -s -X POST -H "Content-Type: application/json" https://kdzd3rnjs57a6c5r6ygpgpog6y0oditl.lambda-url.us-east-1.on.aws \
		-d '{"name":"John","age":30}' | jq .
	```
    2. Send request: `curl -s "https://swawdhqnyns3724nhpr5flhkkm0fwcsz.lambda-url.us-east-1.on.aws/?name=John&age=30" | jq .`

## Cleanup
1. Delete function `kata-body-parameters-function`

## History
