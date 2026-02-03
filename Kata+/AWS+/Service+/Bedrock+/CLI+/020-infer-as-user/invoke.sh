aws bedrock-runtime invoke-model \
	--profile $PROFILE_USER \
	--model-id $MODEL \
	--body file://invoke-model-body.json \
	--cli-binary-format raw-in-base64-out \
	/dev/stdout \
| jq