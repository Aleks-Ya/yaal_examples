# 010-identity-pool-guest-access

## Task
Create an Identity Pool providing anonymous Guest Access.

## Steps
1. Create Identity Pool
	1. Configure identity pool trust
		1. Authentication
			1. Guest access: enabled
	2. Guest role
		1. Create a new IAM role
		2. IAM role name: `kata-identity-pool-guest-access-guest-role`
	3. Identity pool name: `kata-identity-pool-guest-access-identity-pool`
2. Create an Identity
```shell
curl -X POST \
	-H "Content-Type: application/x-amz-json-1.1" \
	-H "X-Amz-Target: AWSCognitoIdentityService.GetId" \
	-d '{"IdentityPoolId":"us-east-1:eada1150-8c16-40a1-8c4d-d473b26e54c5"}' \
	https://cognito-identity.us-east-1.amazonaws.com
```
3. Get Credentials for Identity
```shell
curl -s -X POST \
	-H "Content-Type: application/x-amz-json-1.1" \
	-H "X-Amz-Target: AWSCognitoIdentityService.GetCredentialsForIdentity" \
	-d '{"IdentityId": "us-east-1:79eafc1d-bff5-c9c4-1e98-cc6ae15bd247"}' \
	https://cognito-identity.us-east-1.amazonaws.com \
| jq .
```
4. Use credentials
```shell
export AWS_ACCESS_KEY_ID=ASIAXT2X4DW7SWQUHJGX
export AWS_SECRET_ACCESS_KEY=...
export AWS_SESSION_TOKEN=...
aws sts get-caller-identity
```

## Cleanup
1. Delete Identity Pool `kata-identity-pool-guest-access-identity-pool`
2. Delete IAM role `kata-identity-pool-guest-access-guest-role`
