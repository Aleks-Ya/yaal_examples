# 001-create-function

## Task
Create a function that converts input text to upper case.

Variants:
1. Using AWS CLI
2. Using AWS Console

## AWS Console variant
### Setup
1. Create a function:
	1. Template: `Author from scratch`
	2. Function name: `upper-case`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
2. Define handler:
```
def lambda_handler(event, context):
    return event.upper()
```
3. Deploy the function
4. Create a test event: `"my text"`
5. Run the test

### Cleanup
1. Delete function `upper-case`
2. Delete the policy `AWSLambdaBasicExecutionRole-xxxx`

## AWS CLI variant (NOT FINISHED)
### Setup
1. Create am Execution Role
	1. Create a policy document: ``
	2. Create a role: `aws iam create-role --role-name MyRoleName --assume-role-policy-document file://path/to/trust-policy.json`
1. Create a function:
```
aws lambda create-function \
--function-name UpperCase \
--runtime python3.8 \
--role arn:aws:iam::123456789012:role/execution_role \
--handler lambda_function.lambda_handler \
--zip-file fileb://my-deployment-package.zip
```
