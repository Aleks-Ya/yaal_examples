# 040-user-passes-role-to-service

## Task
A User passes a Role to an AWS Service.
1. There is a Role with policy `AmazonS3ReadOnlyAccess` allowing list buckets
2. There is a Lambda Function which lists buckets, but it cannot assume the Role
3. There is a User who can assume the Role
4. To list buckets, the User assigns the Role to the Function during creation because the User can pass the role

## Steps
1. Prepare terminal
	1. Open new terminal
	2. Change the current directory
	3. Set environment variables
		```shell
		set -x
		export USER=kata-user-user-passes-role-to-service
		export POLICY_NAME=kata-policy-user-passes-role-to-service
		export POLICY_ARN=arn:aws:iam::523633434047:policy/$POLICY_NAME
		export POLICY_S3=arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
		export PROFILE=kata-profile-user-passes-role-to-service
		export REGION=us-east-1
		export ROLE_NAME=kata-role-user-passes-role-to-service
		export ROLE_ARN=arn:aws:iam::523633434047:role/$ROLE_NAME
		export FUNCTION=kata-f-user-passes-role-to-service
		```
2. Create the User:
	1. Create policy for managing the function: 
		`aws iam create-policy --policy-name $POLICY_NAME --policy-document file://user-policy.json`
	2. Create a user: `aws iam create-user --user-name $USER`
	3. Attach policy: `aws iam attach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
	4. Create an access key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
	5. Configure a profile:
		```shell
		export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
		aws --profile $PROFILE configure set aws_access_key_id $KEY_ID
		aws --profile $PROFILE configure set aws_secret_access_key $(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
		aws --profile $PROFILE configure set region $REGION
		```
	6. Test profile: `aws --profile $PROFILE sts get-caller-identity`
3. Create the Role: 
	1. Create a role:
		`aws iam create-role --role-name $ROLE_NAME --assume-role-policy-document file://trust-policy.json`
	2. Attach `AmazonS3ReadOnlyAccess` to the Role: 
		`aws iam attach-role-policy --role-name $ROLE_NAME --policy-arn $POLICY_S3`
4. Create the Function:
	1. Pack the handler: `zip deployment-package.zip handler.py`
	2. Create a function:
		```shell
		aws --profile $PROFILE lambda create-function \
			--function-name $FUNCTION \
			--runtime python3.14 \
			--role $ROLE_ARN \
			--handler handler.lambda_handler \
			--zip-file fileb://deployment-package.zip
		```
	3. Test function: `aws lambda invoke --function-name $FUNCTION /dev/stdout`

## Cleanup
1. Delete the User: 
	1. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
	2. Delete policy: 
		1. Detach policy: `aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
		2. Delete policy: `aws iam delete-policy --policy-arn $POLICY_ARN`
	3. Delete user: `aws iam delete-user --user-name $USER`
2. Delete the Role: 
	1. Detach policy: 
		`aws iam detach-role-policy --role-name $ROLE --policy-arn $POLICY_S3`
	2. Delete role: `aws iam delete-role --role-name $ROLE`
3. Delete the Function: `aws lambda delete-function --function-name $FUNCTION`
4. Remove user profile: 
	1. Open `subl ~/.aws/credentials`
	2. Remove section: `[kata-profile-user-passes-role-to-service]`
5. Close the terminal

## History
- 2026-01-29 success
