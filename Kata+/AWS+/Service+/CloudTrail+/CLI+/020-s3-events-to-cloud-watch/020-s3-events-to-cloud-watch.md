# S3 Bucket events to CloudTrail and to CloudWatch

## Description
Collect events of a S3 Bucket to CloudTrail and then to CloudWatch.

## Steps
1. Create a S3 Bucket
	1. Create Bucket: `aws s3 mb s3://s3-trail-bucket-49123`
	2. Update Bucket Policy: `aws s3api put-bucket-policy --bucket s3-trail-bucket-49123 --policy file://bucket-policy.json`
2. Create a CloudWatch Log Group: `aws logs create-log-group --log-group-name s3-bucket-log-group`
3. Create a Trail: 
	1. Create Role for publishing: 
		1. Create Role: `aws iam create-role --role-name S3CloudTrailRole --assume-role-policy-document file://trust-policy.json`
		2. Attach Policy: `aws iam attach-role-policy --role-name S3CloudTrailRole --policy-arn arn:aws:iam::aws:policy/CloudWatchFullAccessV2`
	2. Create:
	```shell
	aws cloudtrail create-trail --name s3-trail --s3-bucket-name s3-trail-bucket-49123 \
		--cloud-watch-logs-log-group-arn arn:aws:logs:us-east-1:523633434047:log-group:s3-bucket-log-group:* \
		--cloud-watch-logs-role-arn arn:aws:iam::523633434047:role/S3CloudTrailRole
	```
	3. Start logging: `aws cloudtrail start-logging --name s3-trail`
4. Perform action in the bucket: `echo abc | aws s3 cp - s3://s3-trail-bucket-49123/data.txt`
5. See logs (prefix `AWSLogs/523633434047/CloudTrail`): `aws s3 ls --recursive s3://s3-trail-bucket-49123`
6. See log events: `aws logs tail --follow s3-bucket-log-group --log-stream-names 523633434047_CloudTrail_us-east-1`

## Cleanup
1. Delete Trail: `aws cloudtrail delete-trail --name s3-trail`
2. Delete Log Group: `aws logs delete-log-group --log-group-name s3-bucket-log-group`
3. Delete Bucket: `aws s3 rb --force s3://s3-trail-bucket-49123`
4. Delete Role: 
	1. Detach Policy: `aws iam detach-role-policy --role-name S3CloudTrailRole --policy-arn arn:aws:iam::aws:policy/CloudWatchFullAccessV2`
	2. Delete Role: `aws iam delete-role --role-name S3CloudTrailRole`

## History
