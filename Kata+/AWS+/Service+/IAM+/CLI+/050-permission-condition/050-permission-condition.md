# 050-permission-condition

## Task
Create an IAM Policy containing a Permission with Condition.

## Steps
1. Change the current directory
2. Set environment variables
	```shell
	set -x
	export USER=kata-user-permission-condition
	export PROFILE=kata-profile-permission-condition
	export POLICY_NAME=kata-policy-permission-condition
	export POLICY_ARN=arn:aws:iam::523633434047:policy/$POLICY_NAME
	```
3. Create an IAM user:
	1. Create User: `aws iam create-user --user-name $USER`
	2. Create Access Key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
	3. Configure a profile:
		```shell
		export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
		aws --profile $PROFILE configure set aws_access_key_id $KEY_ID
		aws --profile $PROFILE configure set aws_secret_access_key $(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
		```
	4. Test: `aws --profile $PROFILE sts get-caller-identity`
4. Create Policy:
	1. Create a Policy: `aws iam create-policy --policy-name $POLICY_NAME --policy-document file://policy.json`
	2. Attach Policy to user: `aws iam attach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
6. Test access without tag (denied): `aws --profile $PROFILE iam list-users`
7. Test access with tag:
	1. Add tag: `aws iam tag-user --user-name $USER --tags Key=access,Value=allowed`
	2. Test (allowed): `aws --profile $PROFILE iam list-users`
8. Test access without tag:
	1. Remove tag: `aws iam untag-user --user-name $USER --tag-keys access`
	2. Test (denied): `aws --profile $PROFILE iam list-users`

## Clean up
1. Delete profile: `crudini --del ~/.aws/credentials $PROFILE`
2. Delete Access Key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
3. Delete Policy:
	1. Detach Policy from User: `aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
	2. Delete Policy: `aws iam delete-policy --policy-arn $POLICY_ARN`
4. Delete User `aws iam delete-user --user-name $USER`
5. Unset env vars: `set +x; unset USER PROFILE POLICY_NAME POLICY_ARN CREDS KEY_ID`

## History
- 2025-11-22 success