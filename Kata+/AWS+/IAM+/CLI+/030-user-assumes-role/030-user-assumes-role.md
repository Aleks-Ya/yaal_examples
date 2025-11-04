# 030-user-assumes-role

## Task
Status: ?
A User assumes a Role.
Description:
1. There is a S3 bucket `kata-bucket-user-assumes-role`
2. There is a user `kata-user-user-assumes-role`
3. User `kata-user-user-assumes-role` has no access to bucket `kata-bucket-user-assumes-role`
4. There is a role `kata-role-user-assumes-role`
5. Role `kata-role-user-assumes-role` has read access to bucket `kata-bucket-user-assumes-role` (declared in the bucket policy `bucket-policy.json`)
6. User `kata-user-user-assumes-role` assumes role `kata-role-user-assumes-role` and get access to bucket `kata-bucket-user-assumes-role`

## Steps
1. Create a user
	1. Create a user: `aws iam create-user --user-name kata-user-user-assumes-role`
	2. Create an access key: `aws iam create-access-key --user-name kata-user-user-assumes-role`
	3. Configure profile: `aws --profile kata-user-user-assumes-role configure`
	4. Test profile: `aws --profile kata-user-user-assumes-role sts get-caller-identity`
2. Create a role having access to the bucket (to be assumed by the user): `aws iam create-role --role-name kata-role-user-assumes-role --assume-role-policy-document file://trust-policy.json`
3. Create a bucket
	1. Create an S3 bucket: `aws s3 mb s3://kata-bucket-user-assumes-role`
	2. Set bucket policy: `aws s3api put-bucket-policy --bucket kata-bucket-user-assumes-role --policy file://bucket-policy.json`
4. Test assume role
	1. Try to access S3 without assuming: `aws --profile kata-user-user-assumes-role s3 ls s3://kata-bucket-user-assumes-role`
	2. Assume: `CREDS=$(aws --profile kata-user-user-assumes-role sts assume-role --output json --role-arn arn:aws:iam::523633434047:role/kata-role-user-assumes-role --role-session-name Session1)`
	3. Configure profile `assumed-role`:
	```shell
	aws --profile assumed-role configure set aws_access_key_id $(echo $CREDS | jq -r '.CREDSentials.AccessKeyId')
	aws --profile assumed-role configure set aws_secret_access_key $(echo $CREDS | jq -r '.CREDSentials.SecretAccessKey')
	aws --profile assumed-role configure set aws_session_token $(echo $CREDS | jq -r '.CREDSentials.SessionToken')
	```
	4. Try to access S3 with assuming: `aws --profile assumed-role s3 ls s3://kata-bucket-user-assumes-role`

## Cleanup
1. Delete user: 
	1. Delete access key
		1. Find access key ID: `aws iam list-access-keys --output table --user-name kata-user-user-assumes-role`
		2. Delete access key: `aws iam delete-access-key --user-name kata-user-user-assumes-role --access-key-id AKIAXT2X4DW7TFHFEKXH`
	2. Delete user: `aws iam delete-user --user-name kata-user-user-assumes-role`
2. Delete role: `aws iam delete-role --role-name kata-role-user-assumes-role`
3. Delete S3 bucket: `aws s3 rb s3://kata-bucket-user-assumes-role`
4. Remove user profile: open `subl ~/.aws/CREDSentials` and remove sections `[kata-user-user-assumes-role]` and `[assumed-role]`

## History
