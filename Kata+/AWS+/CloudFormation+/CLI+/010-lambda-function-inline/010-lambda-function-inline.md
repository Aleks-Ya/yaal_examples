# 010-lambda-function-inline

## Task
Use CloudFormation to create a Lambda Function from an inline deployment package.

## Steps
1. Change current dir
2. Set environment variables
	```shell
	set -x
	export STACK=kata-stack-lambda-function-inline
	export FUNCTION=kata-f-lambda-function-inline
	export BUCKET=kata-bucket-lambda-function-inline
	```
3. Create stack: 
	1. Create a Transfer Bucket: `aws s3 mb s3://$BUCKET`
	2. Deploy the template:
		```shell
		aws cloudformation deploy \
			--stack-name $STACK \
			--template-file template.yaml \
			--s3-bucket $BUCKET \
			--capabilities CAPABILITY_NAMED_IAM
		```
4. Invoke the Function: `aws lambda invoke --function-name $FUNCTION /dev/stdout`

## Cleanup
1. Delete stack
	1. Delete: `aws cloudformation delete-stack --stack-name $STACK`
	2. Wait: `aws cloudformation wait stack-delete-complete --stack-name $STACK`
2. Delete Transfer Bucket `aws s3 rb --force s3://$BUCKET`
3. Unset env vars: `set +x; unset STACK FUNCTION BUCKET`

## History
- 2025-11-08 success
