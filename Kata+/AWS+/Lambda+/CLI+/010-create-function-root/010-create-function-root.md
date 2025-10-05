# 010-create-function-root

## Task
Status: ?
Create a hello-world function using AWS CLI as the Root User.

## Setup
1. Change the current directory
2. Create an Execution Role: 
```shell
aws iam create-role \
	--role-name kata-role-create-function-root \
	--assume-role-policy-document file://trust-policy.json
```
3. Create a Deployment Package: `zip deployment-package.zip handler.py`
4. Create a function:
```shell
aws lambda create-function \
	--function-name kata-function-create-function-root \
	--runtime python3.13 \
	--role arn:aws:iam::523633434047:role/kata-role-create-function-root\
	--handler handler.lambda_handler \
	--zip-file fileb://deployment-package.zip
```
5. Test function: `aws lambda invoke --function-name kata-function-create-function-root /dev/stdout`

## Cleanup
1. Delete Function: `aws lambda delete-function --function-name kata-function-create-function-root`
2. Delete execution role: `aws iam delete-role --role-name kata-role-create-function-root`
