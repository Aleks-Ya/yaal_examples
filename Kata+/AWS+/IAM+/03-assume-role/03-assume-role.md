# Using assume role

## Description
1. There is a S3 bucket `bucket-tmp-1`
2. There is a user `user1`
3. User `user1` has no access to bucket `bucket-tmp-1`
4. There is a role `AssumedRole1`
5. Role `AssumedRole1` has read access to bucket `bucket-tmp-1` (declared in the bucket policy `bucket-policy.json`)
6. User `user1` assumes role `AssumedRole1` and get access to bucket `bucket-tmp-1`

## Setup
1. Create a user
	1. Create a user: `aws iam create-user --user-name user1`
	2. Create an access key: `aws iam create-access-key --user-name user1`
	3. Configure profile: `aws --profile user1 configure`
	4. Test profile: `aws --profile user1 sts get-caller-identity`
2. Create a role having access to the bucket (to be assumed by the user): `aws iam create-role --role-name AssumedRole1 --assume-role-policy-document file://assume-role-policy.json`
3. Create a bucket
	1. Create an S3 bucket: `aws s3 mb s3://bucket-tmp-1`
	2. Set bucket policy: `aws s3api put-bucket-policy --bucket bucket-tmp-1 --policy file://bucket-policy.json`
4. Test assume role
	1. Try to access S3 without assuming: `aws --profile user1 s3 ls s3://bucket-tmp-1`
	2. Assume: `RESPONSE=$(aws --profile user1 sts assume-role --output json --role-arn arn:aws:iam::523633434047:role/AssumedRole1 --role-session-name Session1)`
	3. Configure profile `assumed-role`:
	```
	aws --profile assumed-role configure set aws_access_key_id $(echo $RESPONSE | jq -r '.Credentials.AccessKeyId')
	aws --profile assumed-role configure set aws_secret_access_key $(echo $RESPONSE | jq -r '.Credentials.SecretAccessKey')
	aws --profile assumed-role configure set aws_session_token $(echo $RESPONSE | jq -r '.Credentials.SessionToken')
	```
	4. Try to access S3 with assuming: `aws --profile assumed-role s3 ls s3://bucket-tmp-1`

## Cleanup
1. Delete access key
	1. Find access key ID: `aws iam list-access-keys --output table --user-name user1`
	2. Delete access key: `aws iam delete-access-key --user-name user1 --access-key-id AKIAXT2X4DW7TFHFEKXH`
3. Delete user: `aws iam delete-user --user-name user1`
4. Delete role: `aws iam delete-role --role-name AssumedRole1`
5. Delete S3 bucket: `aws s3 rb s3://bucket-tmp-1`
6. Remove user profile: open `subl ~/.aws/credentials` and remove sections `[user1]` and `[assumed-role]`
