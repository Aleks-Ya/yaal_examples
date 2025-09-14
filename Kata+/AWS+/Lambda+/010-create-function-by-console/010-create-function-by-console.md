# 010-create-function-by-console

## Task
Create a hello-world function using AWS Console.

## Setup
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `kata-create-function-by-console-function`
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
5. Ivoke from CLI: `aws lambda invoke --function-name kata-create-function-by-console-function /dev/stdout`

## Cleanup
1. Delete function `kata-create-function-by-console-function`
2. Delete execution role `kata-create-function-by-console-function-role-xxxxxx`
