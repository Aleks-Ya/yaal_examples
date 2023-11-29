# Using assume role example

## Description
1. There is a S3 bucket `bucket-tmp-1`
2. There is a user `User1`
3. User `User1` has no access to bucket `bucket-tmp-1`
4. There is a role `AssumedRole1`
5. Role `AssumedRole1` has read access to bucket `bucket-tmp-1` (declared in the bucket policy `bucket-policy.json`)
6. User `User1` assumes role `AssumedRole1` and get access to bucket `bucket-tmp-1`

## Setup
1. Create a role to be assumed by the user: `aws iam create-role --role-name AssumedRole1 --assume-role-policy-document file://assume-role-policy.json`
2. Create an S3 bucket: `aws s3 mb s3://bucket-tmp-1`
3. Set bucket policy: `aws s3api put-bucket-policy --bucket bucket-tmp-1 --policy file://bucket-policy.json`
4. Create a user: `aws iam create-user --user-name User1`
5. Create an access key for `user`: `aws iam create-access-key --user-name User1`
6. Create profile for `User1`: `aws configure --profile User1`
7. Try to access S3 without assuming: `aws --profile User1 s3 ls s3://bucket-tmp-1`
8. Assume: `RESPONSE=$(aws --profile User1 sts assume-role --output json --role-arn arn:aws:iam::523633434047:role/AssumedRole1 --role-session-name Session1)`
9. Configure profile `assumed-role`:
```
aws configure set --profile assumed-role aws_access_key_id $(echo $RESPONSE | jq -r '.Credentials.AccessKeyId')
aws configure set --profile assumed-role aws_secret_access_key $(echo $RESPONSE | jq -r '.Credentials.SecretAccessKey')
aws configure set --profile assumed-role aws_session_token $(echo $RESPONSE | jq -r '.Credentials.SessionToken')
```
10. Try to access S3 with assuming: `aws --profile assumed-role s3 ls s3://bucket-tmp-1`

## Cleanup
1. Find access key ID: `aws iam list-access-keys --output table --user-name User1`
2. Delete access key: `aws iam delete-access-key --user-name User1 --access-key-id AKIAXT2X4DW7TFHFEKXH`
3. Delete user: `aws iam delete-user --user-name User1`
4. Delete role: `aws iam delete-role --role-name AssumedRole1`
5. Delete S3 bucket: `aws s3 rb s3://bucket-tmp-1`
6. Remove user profile: open `subl ~/.aws/credentials` and remove sections `[User1]` and `[assumed-role]`
