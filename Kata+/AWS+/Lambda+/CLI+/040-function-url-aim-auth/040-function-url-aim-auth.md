# 040-function-url-aim-auth

## Task
Status: ?
Invoke a function through its Function URL with IAM authentication.

## Steps
1. Change the current directory
2. Create an Execution Role: `aws iam create-role --role-name ExecutionRole1 --assume-role-policy-document file://trust-policy.json`
3. Pack the handler: `zip deployment-package.zip handler.py`
4. Create a function:
```shell
aws lambda create-function \
--function-name function1 \
--runtime python3.13 \
--role arn:aws:iam::523633434047:role/ExecutionRole1 \
--handler handler.lambda_handler \
--zip-file fileb://deployment-package.zip
```
5. Test function: `aws lambda invoke --function-name function1 /dev/stdout`
6. Create Function URL: `aws lambda create-function-url-config --function-name function1 --auth-type AWS_IAM`
7. Allow function invocation: 
```shell
aws lambda add-permission \
  --function-name function1 \
  --statement-id InvokeUrl \
  --action "lambda:InvokeFunctionUrl" \
  --principal "*" \
  --function-url-auth-type AWS_IAM
```
8. Test: 
  1. No auth (403 Forbidden): `curl -i https://ya6hetq5c7325t7usno7y3dngq0wdvll.lambda-url.us-east-1.on.aws/`
  2. Authenticated: `awscurl --service lambda https://ya6hetq5c7325t7usno7y3dngq0wdvll.lambda-url.us-east-1.on.aws/`

## Cleanup
1. Delete Lambda Function: `aws lambda delete-function --function-name function1`
2. Delete execution role: `aws iam delete-role --role-name ExecutionRole1`

## History
