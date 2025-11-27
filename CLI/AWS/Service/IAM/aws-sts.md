# AWS STS CLI

Show current user details: `aws sts get-caller-identity`
Show other user details: `aws --profile Programmer sts get-caller-identity`

Assume role: 
```shell
aws sts assume-role \
	--role-arn arn:aws:iam::523633434047:role/service-role/role1 \
	--role-session-name session1
```

## Assume role and apply temporary credentials
1. Switch to the role:
```shell
CREDS=$(aws sts assume-role --role-arn arn:aws:iam::523633434047:role/BedrockLimitedAccessRole --role-session-name bedrock_ivoke)
export AWS_ACCESS_KEY_ID=$(echo $CREDS | jq -r '.Credentials.AccessKeyId')
export AWS_SECRET_ACCESS_KEY=$(echo $CREDS | jq -r '.Credentials.SecretAccessKey')
export AWS_SESSION_TOKEN=$(echo $CREDS | jq -r '.Credentials.SessionToken')
```
2. Test: `aws sts get-caller-identity`
3. Switch back: `unset AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY AWS_SESSION_TOKEN`
