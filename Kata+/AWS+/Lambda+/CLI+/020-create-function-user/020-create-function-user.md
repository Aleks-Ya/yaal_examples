# 020-create-function-user

## Task
Status: success
Create a hello-world function using AWS CLI as a custom limited user.

## Setup
1. Change the current directory
2. Set environment variables
```shell
export POLICY=kata-policy-create-function-user
export ROLE=kata-role-create-function-user
export USER=kata-user-create-function-user
export FUNCTION=kata-function-create-function-user
```
3. Create a user for managing Lambda functions:
	1. Create policy for managing Execution Role: `aws iam create-policy --policy-name $POLICY --policy-document file://user-policy.json`
	2. Create user
		1. Create user: `aws iam create-user --user-name $USER`
		2. Attach policies: 
			1. Manage execution role: `aws iam attach-user-policy --user-name $USER --policy-arn arn:aws:iam::523633434047:policy/$POLICY`
			2. Lambda full access: `aws iam attach-user-policy --user-name $USER --policy-arn arn:aws:iam::aws:policy/AWSLambda_FullAccess`
		3. Create access key: `aws iam create-access-key --user-name $USER`
		4. Configure profile: `aws --profile $USER configure` (default region `us-east-1`)
		5. Test profile: `aws --profile $USER lambda list-functions`
4. Create an Execution Role: `aws --profile $USER iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
5. Pack the handler: `zip deployment-package.zip handler.py`
6. Create a function:
```shell
aws --profile $USER lambda create-function \
	--function-name $FUNCTION \
	--runtime python3.13 \
	--role arn:aws:iam::523633434047:role/$ROLE \
	--handler handler.lambda_handler \
	--zip-file fileb://deployment-package.zip
```
7. Test function: `aws --profile $USER lambda invoke --function-name $FUNCTION /dev/stdout`

## Cleanup
1. Delete Lambda Function: `aws --profile $USER lambda delete-function --function-name $FUNCTION`
2. Delete execution role: `aws --profile $USER iam delete-role --role-name $ROLE`
3. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id $(aws --profile $USER configure get aws_access_key_id)`
4. Delete policy:
	1. Manage execution role: `aws iam detach-user-policy --user-name $USER --policy-arn arn:aws:iam::523633434047:policy/$POLICY`
	2. Lambda full access: `aws iam detach-user-policy --user-name $USER --policy-arn arn:aws:iam::aws:policy/AWSLambda_FullAccess`
	3. Delete policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/$POLICY`
5. Delete user: `aws iam delete-user --user-name $USER`
6. Delete profile: edit `~/.aws/credentials`
7. Unset env vars: `unset POLICY ROLE USER FUNCTION`

## History
- 2025-10-14 success
