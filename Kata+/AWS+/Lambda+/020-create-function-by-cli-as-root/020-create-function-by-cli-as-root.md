# Create a Lambda Function from AWS CLI as the root user

## Task
Create a hello-world function using AWS CLI as the Root User.

## Setup
1. Change the current directory
2. Create an Execution Role: `aws iam create-role --role-name ExecutionRole1 --assume-role-policy-document file://trust-policy.json`
3. Pack the handler: `zip deployment-package.zip handler.py`
4. Create a function:
```
aws lambda create-function \
--function-name hello1 \
--runtime python3.12 \
--role arn:aws:iam::523633434047:role/ExecutionRole1 \
--handler handler.lambda_handler \
--zip-file fileb://deployment-package.zip
```
5. Test function: `aws lambda invoke --function-name hello1 /dev/stdout`

## Cleanup
1. Delete Lambda Function: `aws lambda delete-function --function-name hello1`
2. Delete execution role: `aws iam delete-role --role-name ExecutionRole1`
