# A User passes a Role to an AWS Service

## Description
1. There is a role `S3Role` with policy `AmazonS3ReadOnlyAccess` allowing list buckets
2. There is a Lambda function `buckets-function` which lists buckets, but it cannot assume `S3Role`
3. There is a user `FunctionUser` who can assume `S3Role`
4. To list buckets, `FunctionUser` assigns `S3Role` to `buckets-function` during creation because `FunctionUser` can pass the role

## Setup
1. Create `FunctionUser`:
	1. Create policy for managing the function: `aws iam create-policy --policy-name UserPolicy --policy-document file://user-policy.json`
	2. Create a user: `aws iam create-user --user-name FunctionUser`
	3. Attach policy: `aws iam attach-user-policy --user-name FunctionUser --policy-arn arn:aws:iam::523633434047:policy/UserPolicy`
	4. Create an access key: `aws iam create-access-key --user-name FunctionUser`
	5. Configure profile: `aws --profile FunctionUser configure`
	6. Test profile: `aws --profile FunctionUser sts get-caller-identity`
2. Create `S3Role`: 
	1. Create a role: `aws iam create-role --role-name S3Role --assume-role-policy-document file://trust-policy.json`
	2. Attach `AmazonS3ReadOnlyAccess` to `S3Role`: 
	`aws iam attach-role-policy --role-name S3Role --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`
3. Create `buckets-function`:
	1. Pack the handler: `zip deployment-package.zip handler.py`
	2. Create a function:
	```shell
	aws --profile FunctionUser lambda create-function \
	--function-name buckets-function \
	--runtime python3.13 \
	--role arn:aws:iam::523633434047:role/S3Role \
	--handler handler.lambda_handler \
	--zip-file fileb://deployment-package.zip
	```
	3. Test function: `aws lambda invoke --function-name buckets-function /dev/stdout`

## Cleanup
1. Delete `FunctionUser`: 
	1. Delete access key
		1. Find access key ID: `aws iam list-access-keys --output table --user-name FunctionUser`
		2. Delete access key: `aws iam delete-access-key --user-name FunctionUser --access-key-id AKIAXT2X4DW7TFHFEKXH`
	2. Delete policy: 
		1. Detach policy: `aws iam detach-user-policy --user-name FunctionUser --policy-arn arn:aws:iam::523633434047:policy/UserPolicy`
		2. Delete policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/UserPolicy`
	3. Delete user: `aws iam delete-user --user-name FunctionUser`
2. Delete `S3Role`: 
	1. Detach policy: `aws iam detach-role-policy --role-name S3Role --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess`
	2. Delete role: `aws iam delete-role --role-name S3Role`
3. Delete `buckets-function`: `aws lambda delete-function --function-name buckets-function`
4. Remove user profile: 
	1. Open `subl ~/.aws/credentials`
	2. Remove sections: `[FunctionUser]`