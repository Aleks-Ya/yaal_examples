# 001-configure-aws-cli-without-permissions

## Task
Authenticate AWS CLI with new IAM user having no permissions.

## Setup
Use AWS CLI Docker image from `DevOps+/AWS+/AwsDocker/AwsDocker.md`.

1. Create an IAM user
	1. Login to AWS Console using the Root User
	2. Open IAM service
	3. Create new IAM user:
		1. `User name`: `cli`
		2. `Provide user access to the AWS Management Console`: `false`
		3. `Set permissions`: nothing
	4. Create Access Key for user `cli`
		1. `Use case`: `Command Line Interface (CLI)`
		2. Save `Access key` and `Secret access key`
2. Setup CLI
	1. Pull Docker image: `docker pull public.ecr.aws/aws-cli/aws-cli`
	2. Generate a name for config files dir on host machine: `CONF_DIR=/tmp/aws-cli-kata-conf`
	4. Generate a name for shared dir on host machine: `SHARE_DIR=/tmp/aws-cli-kata-share`
	3. Create a folder on the host machine for config files: `mkdir $CONF_DIR`
	4. Create a folder on the host machine for config files: `mkdir $SHARE_DIR`
	4. Create an alias: `alias awsd='docker run --rm -ti -v $CONF_DIR:/root/.aws -v $SHARE_DIR:/root public.ecr.aws/aws-cli/aws-cli'`
	5. Authenticate CLI: `awsd configure`:
		1. `AWS Access Key ID`: copied
		2. `AWS Secret Access Key`: copied
		3. `Default region name`: `us-east-1`
		4. `Default output format`: `text`
	6. Test access: `awsd sts get-caller-identity`

## Clean up
1. Terminal
	1. Delete alias: `unalias awsd`
	2. Remove the conf dir: `rm -rf $CONF_DIR`
	3. Remove the share dir: `rm -rf $SHARE_DIR`
2. AWS Console
	1. Delete user `cli`

## History
1. 2023-12-03 21:30