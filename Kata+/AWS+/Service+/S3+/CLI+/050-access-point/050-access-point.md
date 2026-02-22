# 050-access-point

## Task
Grant access to a Bucket via an Access Point.

## Steps
1. Open a new terminal
2. Change current dir
3. Set env vars
	```shell
	set -x
	export ACCOUNT=523633434047
	export USER=kata-user-access-point
	export PROFILE=kata-profile-access-point
	export BUCKET=kata-bucket-access-point
	export POLICY_NAME=kata-policy-access-point
	export POLICY_ARN=arn:aws:iam::$ACCOUNT:policy/$POLICY_NAME
	export AP_NAME=kata-point-access-point
	```
4. Create user
	1. Create a user: `aws iam create-user --user-name $USER`
	2. Create Access Key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
	3. Configure a profile:
		```shell
		export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
		aws --profile $PROFILE configure set aws_access_key_id $KEY_ID
		aws --profile $PROFILE configure set aws_secret_access_key \
			$(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
		```
	4. Test: `aws --profile $PROFILE sts get-caller-identity`
5. Create a Bucket:
	1. Create a bucket: `aws s3 mb s3://$BUCKET`
	2. Upload a file: `echo abc | aws s3 cp - s3://$BUCKET/data.txt`
6. Create an Access Point: 
	1. Create: `aws s3control create-access-point --account-id $ACCOUNT --name $AP_NAME --bucket $BUCKET`
	2. Delegate access to the AP: `aws s3api put-bucket-policy --bucket $BUCKET --policy file://bucket-policy.json`
	3. Get ARN: `export AP_ARN="arn:aws:s3:us-east-1:$ACCOUNT:accesspoint/$AP_NAME"`
	4. Get alias: 
		```shell
		export AP_ALIAS=$(aws s3control get-access-point --account-id $ACCOUNT --name $AP_NAME \
			--query Alias --output text)
		```
6. Grant access
	1. Create a policy: `aws iam create-policy --policy-name $POLICY_NAME --policy-document file://user-policy.json`
	2. Attach the policy to the user: `aws iam attach-user-policy --policy-arn $POLICY_ARN --user-name $USER`
7. Test access
	1. No access to the Bucket: 
		1. List objects: `aws --profile $PROFILE s3 ls s3://$BUCKET`
	2. Have access via the Access Point:
		1. Via AP Alias:
			1. List objects: `aws --profile $PROFILE s3 ls s3://$AP_ALIAS`
			2. Get object: `aws --profile $PROFILE s3 cp s3://$AP_ALIAS/data.txt -`
			3. Put object: `aws --profile $PROFILE s3 cp data2.txt s3://$AP_ALIAS/data2.txt`
			4. Delete object: `aws --profile $PROFILE s3 rm s3://$AP_ALIAS/data2.txt`
		2. Via AP ARN:
			1. List objects (ARN): `aws --profile $PROFILE s3api list-objects-v2 --bucket $AP_ARN`
			2. Get object: `aws --profile $PROFILE s3api get-object --bucket $AP_ARN --key data.txt /dev/stdout`
			3. Put object: `aws --profile $PROFILE s3api put-object --bucket $AP_ARN --key data2.txt --body data2.txt`
			4. Delete object: `aws --profile $PROFILE s3api delete-object --bucket $AP_ARN --key data2.txt`

## Cleanup
1. Delete the AP: `aws s3control delete-access-point --account-id $ACCOUNT --name $AP_NAME`
2. Delete bucket: `aws s3 rb s3://$BUCKET --force`
3. Delete the policy:
	1. Detach the policy: `aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
	2. Delete policy: `aws iam delete-policy --policy-arn $POLICY_ARN`
4. Delete the user:
	1. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
	2. Delete user: `aws iam delete-user --user-name $USER`
5. Delete profile: `crudini --del ~/.aws/credentials $PROFILE`

## History
- 2026-02-25 success
