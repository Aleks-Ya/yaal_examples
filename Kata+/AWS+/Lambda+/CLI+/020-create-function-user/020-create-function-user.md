# 020-create-function-user

## Task
Status: ?
Create a hello-world function using AWS CLI as a custom limited user.

## Setup
1. Change the current directory
2. Create a user for managing Lambda functions:
	1. Create policy for managing Execution Role: `aws iam create-policy --policy-name kata-function-create-function-user --policy-document file://user-policy.json`
	2. Create user
		1. Create user: `aws iam create-user --user-name user1`
		2. Attach policies: 
			1. Manage execution role: `aws iam attach-user-policy --user-name user1 --policy-arn arn:aws:iam::523633434047:policy/kata-function-create-function-user`
			2. Lambda full access: `aws iam attach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/AWSLambda_FullAccess`
		3. Create access key: `aws iam create-access-key --user-name user1`
		4. Configure profile: `aws --profile user1 configure`
		5. Set default region: `aws --profile user1 configure set region us-east-1`
		6. Test profile: `aws --profile user1 lambda list-functions`
3. Create an Execution Role: `aws --profile user1 iam create-role --role-name ExecutionRole1 --assume-role-policy-document file://trust-policy.json`
4. Pack the handler: `zip deployment-package.zip handler.py`
5. Create a function:
```shell
aws --profile user1 lambda create-function \
--function-name function1 \
--runtime python3.13 \
--role arn:aws:iam::523633434047:role/ExecutionRole1 \
--handler handler.lambda_handler \
--zip-file fileb://deployment-package.zip
```
6. Test function: `aws lambda invoke --function-name function1 /dev/stdout`

## Cleanup
1. Delete Lambda Function: `aws --profile user1 lambda delete-function --function-name function1`
2. Delete execution role: `aws --profile user1 iam delete-role --role-name ExecutionRole1`
3. Delete access key: `aws iam delete-access-key --user-name user1 --access-key-id $(aws --profile user1 configure get aws_access_key_id)`
4. Detach policies:
	1. Manage execution role: `aws iam detach-user-policy --user-name user1 --policy-arn arn:aws:iam::523633434047:policy/kata-function-create-function-user`
	2. Lambda full access: `aws iam detach-user-policy --user-name user1 --policy-arn arn:aws:iam::aws:policy/AWSLambda_FullAccess`
5. Delete policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/kata-function-create-function-user`
6. Delete user: `aws iam delete-user --user-name user1`
