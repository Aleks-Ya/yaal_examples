# 010-create-function-root

## Task
Create a hello-world function using AWS CLI as the Root User.

## Steps
1. Change the current directory
2. Set environment variables
	```shell
	set -x
	export ROLE=kata-role-create-function-root
	export FUNCTION=kata-f-create-function-root
	```
3. Create an Execution Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
4. Create a Deployment Package: `zip deployment-package.zip handler.py`
5. Create a function:
	```shell
	aws lambda create-function \
		--function-name $FUNCTION \
		--runtime python3.13 \
		--role arn:aws:iam::523633434047:role/$ROLE\
		--handler handler.lambda_handler \
		--zip-file fileb://deployment-package.zip
	```
6. Test function: `aws lambda invoke --function-name $FUNCTION /dev/stdout`

## Cleanup
1. Delete Function: `aws lambda delete-function --function-name $FUNCTION`
2. Delete execution role: `aws iam delete-role --role-name $ROLE`
3. Unset env vars: `set +x; unset ROLE FUNCTION`

# History
- 2025-10-14 success
