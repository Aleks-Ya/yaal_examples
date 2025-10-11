# A Role assumes another Role

## Description
1. There is a role `S3Role` with policy `AmazonS3ReadOnlyAccess` allowing list buckets
2. There is a role `AnalystRole` that can assume `S3Role`
3. There is a user `AnalystUser` who can assume `AnalystRole`
4. To list buckets, `AnalystUser` assumes role `AnalystRole` and then assumes `S3Role`

## Setup
1. Create `AnalystUser`:
	1. Create a user: `aws iam create-user --user-name AnalystUser`
	2. Create an access key: `aws iam create-access-key --user-name AnalystUser`
	3. Configure profile: `aws --profile AnalystUser configure`
	4. Test profile: `aws --profile AnalystUser sts get-caller-identity`
2. Create `AnalystRole`: `aws iam create-role --role-name AnalystRole --assume-role-policy-document file://analyst-role-trust-policy.json`
3. Create `S3Role`: 
	1. Create a role: `aws iam create-role --role-name S3Role --assume-role-policy-document file://s3-role-trust-policy.json`
	2. Attach `AmazonS3ReadOnlyAccess` to `S3Role`: 
	`aws iam attach-role-policy --role-name S3Role --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`
4. Test assume role
	1. Try to access S3 without assuming (denied): `aws --profile AnalystUser s3 ls s3://`
	2. `AnalystUser` assumes `AnalystRole`: 
		1. Assume: `CREDS=$(aws --profile AnalystUser sts assume-role --output json --role-arn arn:aws:iam::523633434047:role/AnalystRole --role-session-name AnalystSession)`
		2. Configure profile `AnalystSessionProfile`:
		```shell
		aws --profile AnalystSessionProfile configure set aws_access_key_id $(echo $CREDS | jq -r '.CREDSentials.AccessKeyId')
		aws --profile AnalystSessionProfile configure set aws_secret_access_key $(echo $CREDS | jq -r '.CREDSentials.SecretAccessKey')
		aws --profile AnalystSessionProfile configure set aws_session_token $(echo $CREDS | jq -r '.CREDSentials.SessionToken')
		```
		3. Try to access S3 with assuming (denied): `aws --profile AnalystSessionProfile s3 ls s3://`
	3. `AnalystRole` assumes `S3Role`: 
		1. Assume: `CREDS=$(aws --profile AnalystSessionProfile sts assume-role --output json --role-arn arn:aws:iam::523633434047:role/S3Role --role-session-name S3Session)`
		2. Configure profile `S3SessionProfile`:
		```shell
		aws --profile S3SessionProfile configure set aws_access_key_id $(echo $CREDS | jq -r '.CREDSentials.AccessKeyId')
		aws --profile S3SessionProfile configure set aws_secret_access_key $(echo $CREDS | jq -r '.CREDSentials.SecretAccessKey')
		aws --profile S3SessionProfile configure set aws_session_token $(echo $CREDS | jq -r '.CREDSentials.SessionToken')
		```
		3. Try to access S3 with assuming (allowed): `aws --profile S3SessionProfile s3 ls s3://`

## Cleanup
1. Delete `AnalystUser`: 
	1. Delete access key
		1. Find access key ID: `aws iam list-access-keys --output table --user-name AnalystUser`
		2. Delete access key: `aws iam delete-access-key --user-name AnalystUser --access-key-id AKIAXT2X4DW7TFHFEKXH`
	2. `aws iam delete-user --user-name AnalystUser`
2. Delete `AnalystRole`: `aws iam delete-role --role-name AnalystRole`
3. Delete `S3Role`: 
	1. Detach policy: `aws iam detach-role-policy --role-name S3Role --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`
	2. Delete role: `aws iam delete-role --role-name S3Role`
4. Remove user profile: 
	1. Open `subl ~/.aws/CREDSentials`
	2. Remove sections: `[AnalystUser]`, `[AnalystSessionProfile]`, `[S3SessionProfile]`
