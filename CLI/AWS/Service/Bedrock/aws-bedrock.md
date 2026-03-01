# AWS Bedrock CLI

List models: `aws bedrock list-foundation-models`
List models for embeddings: `aws bedrock list-foundation-models --by-output-modality EMBEDDING`
Show model details by ID: `aws bedrock get-foundation-model --model-identifier 'amazon.titan-embed-text-v2:0'`

Invoke a model:
```shell
aws bedrock-runtime invoke-model \
	--profile acc3 \
	--model-id amazon.nova-lite-v1:0 \
	--body file://invoke-model-body.json \
	--cli-binary-format raw-in-base64-out \
	/tmp/response.json
```

Infer embeddings:
```shell
BODY='{"inputText":"My interesting text","dimensions":256,"normalize":true,"embeddingTypes":["float"]}'
BODY64=$(echo -n "$BODY" | base64)
aws bedrock-runtime invoke-model --model-id amazon.titan-embed-text-v2:0 --body "$BODY64" /tmp/response.json
```

Assume a role before inferring:
```shell
CREDS=$(aws sts assume-role --role-arn arn:aws:iam::523633434047:role/BedrockLimitedAccessRole --role-session-name bedrock_ivoke)
export AWS_ACCESS_KEY_ID=$(echo $CREDS | jq -r '.Credentials.AccessKeyId')
export AWS_SECRET_ACCESS_KEY=$(echo $CREDS | jq -r '.Credentials.SecretAccessKey')
export AWS_SESSION_TOKEN=$(echo $CREDS | jq -r '.Credentials.SessionToken')
```

## Model Invocation Job
List all jobs: `aws bedrock list-model-invocation-jobs`
List in-progress jobs: `aws bedrock list-model-invocation-jobs --status InProgress`
Get job ID by job name:
```shell
aws bedrock list-model-invocation-jobs \
  --query "modelInvocationJobSummaries[?jobName=='MY_JOB_NAME'].[jobArn]" \
  --output text
```
Get job: `aws bedrock get-model-invocation-job --job-identifier <job-id>`
Create a model invocation job:
```shell
aws bedrock create-model-invocation-job \
  --job-name my-batch-job \
  --role-arn arn:aws:iam::<account-id>:role/<bedrock-batch-role> \
  --model-id <foundation-model-id> \
  --input-data-config s3Uri=s3://<bucket>/<input-prefix>/,s3InputFormat=JSONL \
  --output-data-config s3Uri=s3://<bucket>/<output-prefix>/
```
Cancel a job: `aws bedrock cancel-model-invocation-job --job-identifier <JOB_ID>`
	