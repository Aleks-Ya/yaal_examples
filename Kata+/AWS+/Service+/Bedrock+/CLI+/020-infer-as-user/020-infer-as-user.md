# 020-infer-as-user

## Task
Infer a model as an IAM User.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export REGION=us-east-1
	export PROFILE_ROOT=acc3
	export USER=kata-user-infer-as-user
	export PROFILE_USER=kata-profile-infer-as-user
	export MODEL=amazon.nova-lite-v1:0
	export ROLE=arn:aws:iam::aws:policy/AmazonBedrockLimitedAccess
	```
4. Create an IAM user:
	1. Create User: `aws iam create-user --profile $PROFILE_ROOT --user-name $USER`
	2. Create Access Key: `export CREDS=$(aws iam create-access-key --profile $PROFILE_ROOT --user-name $USER)`
	3. Configure a profile:
		```shell
		export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
		aws --profile $PROFILE_USER configure set aws_access_key_id $KEY_ID
		aws --profile $PROFILE_USER configure set aws_secret_access_key \
			$(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
		aws --profile $PROFILE_USER configure set region $REGION
		```
	4. Test: `aws --profile $PROFILE_USER sts get-caller-identity`
5. Try to invoke the model without permissions: `./invoke.sh`
6. Grant permissions: `aws iam attach-user-policy --profile $PROFILE_ROOT --user-name $USER --policy-arn $ROLE`
7. Invoke the model: `./invoke.sh`

## Cleanup
1. Delete profile: `crudini --del ~/.aws/credentials $PROFILE_USER`
2. Delete Access Key: `aws iam delete-access-key --profile $PROFILE_ROOT --user-name $USER --access-key-id $KEY_ID`
3. Detach the Role: `aws iam detach-user-policy --profile $PROFILE_ROOT --user-name $USER --policy-arn $ROLE`
4. Delete User `aws iam delete-user --profile $PROFILE_ROOT --user-name $USER`
5. Close the terminal

## History
- 2026-02-04 success
