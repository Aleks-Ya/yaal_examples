# 020-user-assumes-role

## Task
A User assumes a Role.

Scenario:
1. There are an S3 Bucket, a User, and a Role
2. The User has no access to the Bucket
3. The Role has access to the Bucket
4. The User assumes the Role and gets access to the Bucket

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export REGION=us-east-1
	export USER=kata-user-user-assumes-role
	export ROLE=kata-role-user-assumes-role
	export BUCKET=kata-bucket-user-assumes-role
	```
4. Create a user
	1. Create a user: `aws iam create-user --user-name $USER`
	2. Create an access key: `export CREDS1=$(aws iam create-access-key --user-name $USER)`
	3. Configure profile:
		```shell
		export KEY_ID=$(echo $CREDS1 | jq -r '.AccessKey.AccessKeyId')
		aws --profile $USER configure set aws_access_key_id $KEY_ID
		aws --profile $USER configure set aws_secret_access_key $(echo $CREDS1 | jq -r '.AccessKey.SecretAccessKey')
		aws --profile $USER configure set region $REGION
		```
	4. Test profile: `aws --profile $USER sts get-caller-identity`
5. Create a role having access to the bucket (to be assumed by the user): 
	`aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
6. Create a bucket
	1. Create an S3 bucket: `aws s3 mb s3://$BUCKET`
	2. Set bucket policy: `aws s3api put-bucket-policy --bucket $BUCKET --policy file://bucket-policy.json`
7. Test assume role
	1. Try to access S3 without assuming: `aws --profile $USER s3 ls s3://$BUCKET`
	2. Assume: 
		```shell
		CREDS2=$(aws --profile $USER sts assume-role --output json \
			--role-arn arn:aws:iam::523633434047:role/$ROLE --role-session-name Session1)
		```
	3. Configure the Role profile:
		```shell
		aws --profile $ROLE configure set aws_access_key_id \
			$(echo $CREDS2 | jq -r '.Credentials.AccessKeyId')
		aws --profile $ROLE configure set aws_secret_access_key \
			$(echo $CREDS2 | jq -r '.Credentials.SecretAccessKey')
		aws --profile $ROLE configure set aws_session_token \
			$(echo $CREDS2 | jq -r '.Credentials.SessionToken')
		```
	4. Test profile: `aws --profile $ROLE sts get-caller-identity`
	5. Access S3 with assuming: `aws --profile $ROLE s3 ls s3://$BUCKET`

## Cleanup
1. Delete user: 
	1. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
	2. Delete user: `aws iam delete-user --user-name $USER`
2. Delete role: `aws iam delete-role --role-name $ROLE`
3. Delete S3 bucket: `aws s3 rb s3://$BUCKET`
4. Remove user profiles: 
	1. `crudini --del ~/.aws/credentials $USER`
	2. `crudini --del ~/.aws/credentials $ROLE`
5. Close the terminal

## History
- 2026-02-04 success
