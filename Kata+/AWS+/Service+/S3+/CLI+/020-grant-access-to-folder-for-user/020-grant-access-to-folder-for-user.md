# 020-grant-access-to-folder-for-user

## Task
Grant read-write access to a folder in a bucket for a user.

## Steps
1. Open a new terminal
2. Change current dir
3. Set env vars
	```shell
	set -x
	export ACCOUNT=523633434047
	export USER=kata-user-grant-access-to-folder-for-user
	export PROFILE=kata-profile-grant-access-to-folder-for-user
	export BUCKET=kata-bucket-grant-access-to-folder-for-user
	export POLICY_NAME=kata-policy-grant-access-to-folder-for-user
	export POLICY_ARN=arn:aws:iam::$ACCOUNT:policy/$POLICY_NAME
	```
4. Create user
	1. Create a user: `aws iam create-user --user-name $USER`
	2. Create Access Key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
	3. Configure a profile:
		```shell
		export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
		aws --profile $PROFILE configure set aws_access_key_id $KEY_ID
		aws --profile $PROFILE configure set aws_secret_access_key \
			$(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
		```
	4. Test: `aws --profile $PROFILE sts get-caller-identity`
5. Create bucket
	1. Create a bucket: `aws s3 mb s3://$BUCKET`
	2. Upload a file (accessible): `echo abc | aws s3 cp - s3://$BUCKET/dir1/data.txt`
	3. Upload a file (inaccessible): `echo abc | aws s3 cp - s3://$BUCKET/dir2/secret.txt`
6. Grant access
	1. Create a policy: `aws iam create-policy --policy-name $POLICY_NAME --policy-document file://policy.json`
	2. Attach the policy to the user: `aws iam attach-user-policy --policy-arn $POLICY_ARN --user-name $USER`
7. Test access
	1. Can do:
		1. List files: `aws --profile $PROFILE s3 ls s3://$BUCKET/dir1/`
		2. Get file content: `aws --profile $PROFILE s3 cp s3://$BUCKET/dir1/data.txt -`
		2. Update file: `echo xyz | aws --profile $PROFILE s3 cp - s3://$BUCKET/dir1/data.txt`
		3. Delete file: `aws s3 rm s3://$BUCKET/dir1/data.txt`
	2. Cannot do:
		1. Get file from another folder: `aws --profile $PROFILE s3 cp s3://$BUCKET/dir2/secret.txt -`

## Cleanup
1. Delete bucket: `aws s3 rb s3://$BUCKET --force`
2. Delete the policy:
	1. Detach the policy: `aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
	2. Delete policy: `aws iam delete-policy --policy-arn $POLICY_ARN`
3. Delete the user:
	1. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
	2. Delete user: `aws iam delete-user --user-name $USER`
4. Delete profile: `crudini --del ~/.aws/credentials $PROFILE`
5. Close the terminal

## History
- 2026-02-25 success
