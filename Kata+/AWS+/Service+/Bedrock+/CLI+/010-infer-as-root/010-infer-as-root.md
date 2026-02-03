# 010-infer-as-root

## Task
Infer a model as the root user.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export PROFILE_ROOT=acc3
	export MODEL=amazon.nova-lite-v1:0
	```
4. Invoke the model by AWS CLI:
	1. Invoke
		```shell
		aws bedrock-runtime invoke-model \
			--profile $PROFILE_ROOT \
			--model-id $MODEL \
			--body file://invoke-model-body.json \
			--cli-binary-format raw-in-base64-out \
			/tmp/kata-infer-as-root.json
		```
	2. Result: `compact-json /tmp/kata-infer-as-root.json`
5. Invoke the model by cURL with a Bedrock API Key
	1. Save Bedrock API key to file `/tmp/bedrock-api-key.txt`
	2. Send a request:
		```shell
		curl -s https://bedrock-runtime.us-east-1.amazonaws.com/model/$MODEL/invoke \
			-H 'Content-Type: application/json' \
			-H "Authorization: Bearer $(cat /tmp/bedrock-api-key.txt)" \
			--data @invoke-model-body.json \
		| compact-json -
		```
6. Invoke the model by AWS cURL with an AWS Access Key
	```shell
	awscurl https://bedrock-runtime.us-east-1.amazonaws.com/model/$MODEL/invoke \
		--profile $PROFILE_ROOT \
		--service bedrock \
		-X POST \
		-H "Content-Type: application/json" \
		-d @invoke-model-body.json \
	| compact-json -
	```

## Cleanup

## History

