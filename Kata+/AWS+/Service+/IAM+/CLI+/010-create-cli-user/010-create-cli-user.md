# 010-create-cli-user

## Task
Create an IAM user and authenticate AWS CLI with it.

## Steps
1. Set environment variables
	```shell
	set -x
	export USER=kata-user-create-cli-user
	export PROFILE=kata-profile-create-cli-user
	```
2. Create an IAM user:
	1. Create User: `aws iam create-user --user-name $USER`
	2. Create Access Key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
3. Configure a profile:
	```shell
	export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
	aws --profile $PROFILE configure set aws_access_key_id $KEY_ID
	aws --profile $PROFILE configure set aws_secret_access_key $(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
	```
4. Test: `aws --profile $PROFILE sts get-caller-identity`

## Clean up
1. Delete profile: `crudini --del ~/.aws/credentials $PROFILE`
2. Delete Access Key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
3. Delete User `aws iam delete-user --user-name $USER`
4. Unset env vars: `set +x; unset USER PROFILE CREDS KEY_ID`

## History
- 2025-11-22 success
