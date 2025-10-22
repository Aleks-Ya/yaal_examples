# 010-create-function

## Task
Status: ?
Create a hello-world function using AWS Console.

## Setup
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `kata-f-create-function`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
2. Define handler:
```python
def lambda_handler(event, context):
    return "Hello!"
```
3. Deploy the function
4. Send a test event
5. Ivoke from CLI: `aws lambda invoke --function-name kata-f-create-function /dev/stdout`

## Cleanup
1. Delete function `kata-f-create-function`
2. Delete execution role `kata-f-create-function-role-xxxxxx`

## History
