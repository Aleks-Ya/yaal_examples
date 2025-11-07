# 030-s3-bucket-empty

## Task
Use CloudFormation to create an empty S3 Bucket.

## Steps
1. Change current dir
2. Set environment variables
	```shell
	set -x
	export STACK=kata-stack-s3-bucket-empty
	export BUCKET=kata-bucket-s3-bucket-empty
	export TRANSFER_BUCKET=kata-bucket-s3-bucket-empty-transfer
	```
3. Create stack: 
	1. Create a Transfer Bucket: `aws s3 mb s3://$TRANSFER_BUCKET`
	2. Deploy the template:
		```shell
		aws cloudformation deploy \
			--stack-name $STACK \
			--template-file template.yaml \
			--s3-bucket $TRANSFER_BUCKET
		```
4. List object in the Bucket: `aws s3 ls s3://$BUCKET`

## Cleanup
1. Delete stack
	1. Delete: `aws cloudformation delete-stack --stack-name $STACK`
	2. Wait: `aws cloudformation wait stack-delete-complete --stack-name $STACK`
2. Delete Transfer Bucket `aws s3 rb --force s3://$TRANSFER_BUCKET`
3. Unset env vars: `set +x; unset STACK BUCKET TRANSFER_BUCKET`

## History
- 2025-11-08 success
