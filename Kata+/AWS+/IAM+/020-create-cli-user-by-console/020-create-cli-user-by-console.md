# 020-create-cli-user-by-console

See `Kata+/AWS+/CLI+/01-configure-aws-cli-without-permissions/01-configure-aws-cli-without-permissions.md`

## Task
Create an IAM user and authenticate AWS CLI with it.

## Setup
1. Create an IAM user
	1. Login to AWS Console as the Root User
	2. Create an IAM user:
		1. User name: `kata-user-create-cli-user-by-console`
		2. Provide user access to the AWS Management Console: `false`
		3. Set permissions: nothing
	3. Create an Access Key
		1. Use case: `Command Line Interface (CLI)`
		2. Save `Access key` and `Secret access key`
2. Configure CLI:
	1. Set profile: `export AWS_PROFILE=kata-profile-create-cli-user-by-console`
	2. Authenticate CLI: `aws configure`:
		1. AWS Access Key ID: copied
		2. AWS Secret Access Key: copied
		3. Default region name: `us-east-1`
		4. Default output format: `text`
	3. Test access: `aws sts get-caller-identity`

## Clean up
1. Terminal
	1. Unset profile: `unset AWS_PROFILE`
	2. Delete profile `kata-profile-create-cli-user-by-console` from `~/.aws/credentials`
2. AWS Console
	1. Delete user `kata-user-create-cli-user-by-console`
