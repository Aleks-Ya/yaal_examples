# 010-create-cli-user

## Task
Status: ?
Create an IAM user and authenticate AWS CLI with it.

## Setup
1. Create an IAM user
	1. Create User: `aws iam create-user --user-name kata-user-create-cli-user`
	2. Create Access Key: `aws iam create-access-key --user-name kata-user-create-cli-user`
2. Configure CLI:
	1. Set profile: `export AWS_PROFILE=kata-profile-create-cli-user`
	2. Authenticate CLI: `aws configure`:
		1. AWS Access Key ID: copied
		2. AWS Secret Access Key: copied
		3. Default region name: `us-east-1`
		4. Default output format: `text`
3. Test access: `aws sts get-caller-identity`

## Clean up
1. Unset profile: `unset AWS_PROFILE`
2. Delete profile `kata-profile-create-cli-user` from `~/.aws/credentials`
3. Delete Access Key: `aws iam delete-access-key --user-name kata-user-create-cli-user --access-key-id AKIAIOSFODNN7EXAMPLE`
4. Delete User `aws iam delete-user --user-name kata-user-create-cli-user`
