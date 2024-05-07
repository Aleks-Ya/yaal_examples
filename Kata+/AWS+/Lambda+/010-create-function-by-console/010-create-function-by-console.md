# 010-create-function-by-console

## Task
Create a hello-world function using AWS Console.

## Setup
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `hello1`
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
4. Create a test event: `"my text"`
5. Run the test

## Cleanup
1. Delete function `hello1`
2. Delete the policy `AWSLambdaBasicExecutionRole-xxxx`
