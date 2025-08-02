# 070-function-url-no-auth

## Task
Invoke a function through its Function URL without authentication.

## Setup
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
6. Create Function URL: `aws lambda create-function-url-config --function-name function1 --auth-type NONE`
7. Allow function invocation: 
```shell
aws lambda add-permission \
  --function-name function1 \
  --statement-id InvokeUrl \
  --action "lambda:InvokeFunctionUrl" \
  --principal "*" \
  --function-url-auth-type NONE
```
8. Test: `curl https://u2o2i4zscotzmimkot5q3rwgiq0pxjyc.lambda-url.us-east-1.on.aws/`

## Cleanup
1. Delete Lambda Function: `aws lambda delete-function --function-name function1`
2. Delete execution role: `aws iam delete-role --role-name ExecutionRole1`
