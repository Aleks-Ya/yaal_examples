# 070-function-error-python

## Task
Status: ?
Create a function that fails and return an error as a Python exception (sync invocation).

## Steps
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `kata-f-error-python-function`
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
def lambda_handler(event, context):
    raise ValueError("Parameter is invalid") 
```
3. Deploy the function
4. Test
	1. Invoke by a test event
	2. Ivoke with CLI: 
		1. Invoke: `aws lambda invoke --function-name kata-f-error-python-function /tmp/function-error-python.json`
		2. Verify body: `jq . /tmp/function-error-python.json`
	3. Invoke via function URL: `curl -i https://sps2jnfy43eirrjnsu26wvg5oe0ynrjo.lambda-url.us-east-1.on.aws`

## Cleanup
1. Delete function's execution role
2. Delete function `kata-f-error-python-function`

## History
