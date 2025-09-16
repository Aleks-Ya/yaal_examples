# Cognito Identity Pool CLI

## Commands
List Identity Pools: `aws cognito-identity list-identity-pools --max-results 5`
List Identities in a Pool: `aws cognito-identity list-identities --max-results 5 --identity-pool-id us-east-1:9dd450aa-feac-4813-8d7c-9727c7cfa2e6`
Generate new Identity in a Pool: `aws cognito-identity get-id --identity-pool-id us-east-1:9dd450aa-feac-4813-8d7c-9727c7cfa2e6`
Get credentials for Identity: `aws cognito-identity get-credentials-for-identity --identity-id us-east-1:79eafc1d-bfed-c52b-54c7-6597de703dfe`

## CURL requests
Create a Guest Identity:
```shell
curl -X POST \
	-H "Content-Type: application/x-amz-json-1.1" \
	-H "X-Amz-Target: AWSCognitoIdentityService.GetId" \
	-d '{"IdentityPoolId":"us-east-1:9dd450aa-feac-4813-8d7c-9727c7cfa2e6"}' \
	https://cognito-identity.us-east-1.amazonaws.com
```

Create a AWS credentials for Identity:
```shell
curl -s -X POST \
	-H "Content-Type: application/x-amz-json-1.1" \
	-H "X-Amz-Target: AWSCognitoIdentityService.GetCredentialsForIdentity" \
	-d '{"IdentityId": "us-east-1:79eafc1d-bfcb-c957-0873-ee2e4415f939"}' \
	https://cognito-identity.us-east-1.amazonaws.com
```
