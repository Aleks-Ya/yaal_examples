# 030-infer-batch

## Task
Infer a model in batch mode.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export AWS_PROFILE=acc3
	export ACCOUNT=121846058060
	export MODEL=amazon.nova-lite-v1:0
	export BUCKET=kata-bucket-infer-batch
	export JOB_NAME=kata-job-infer-batch
	export POLICY_NAME=kata-policy-infer-batch
	export POLICY_ARN=arn:aws:iam::$ACCOUNT:policy/$POLICY_NAME
	export ROLE_NAME=kata-role-infer-batch
	export ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$ROLE_NAME
	export S3_INPUT=s3://$BUCKET/in/input.jsonl
	export S3_OUTPUT=s3://$BUCKET/out/output.jsonl
	```
4. Setup data
	1. Create an S3 bucket: `aws s3 mb s3://$BUCKET`
	2. Upload data to the S3 bucket: `aws s3 cp input.jsonl $S3_INPUT`
5. Create a Batch Inference Job:
	1. Create a role: `aws iam create-role --role-name $ROLE_NAME --assume-role-policy-document file://trust-policy.json`
	2. Create a policy: 
		1. Create a Policy: `aws iam create-policy --policy-name $POLICY_NAME --policy-document file://permission-policy.json`
		2. Attach Policy to user: `aws iam attach-role-policy --role-name $ROLE_NAME --policy-arn $POLICY_ARN`
	3. Create a job:
		```shell
		aws bedrock create-model-invocation-job --job-name $JOB_NAME --role-arn $ROLE_ARN --model-id $MODEL \
	  		--input-data-config "s3InputDataConfig={s3InputFormat=JSONL,s3Uri=s3://$BUCKET/in/}" \
	  		--output-data-config "s3OutputDataConfig={s3Uri=s3://$BUCKET/out/}"
		```
	4. Get job ID:
		```shell
		export JOB_ARN=$(aws bedrock list-model-invocation-jobs \
	  		--query "invocationJobSummaries[?jobName=='$JOB_NAME'].[jobArn]" \
	  		--output text)
		```
	5. Verify job: `aws bedrock get-model-invocation-job --job-identifier $JOB_ARN`
6. 

!!! Error: 
`Batch job contains less records (1) than the required minimum of: 100`


## Cleanup
1. Delete the S3 bucket: `aws s3 rb --force s3://$BUCKET`
2. Delete the Policy
3. Delete the Role
2. Cancel the job (?)
2. Close the terminal

## History
