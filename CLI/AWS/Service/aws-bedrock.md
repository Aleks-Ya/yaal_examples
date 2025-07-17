# AWS Bedrock CLI

List models: `aws bedrock list-foundation-models`
List models for embeddings: `aws bedrock list-foundation-models --by-output-modality EMBEDDING`
Show model details by ID: `aws bedrock get-foundation-model --model-identifier 'amazon.titan-embed-text-v2:0'`

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
