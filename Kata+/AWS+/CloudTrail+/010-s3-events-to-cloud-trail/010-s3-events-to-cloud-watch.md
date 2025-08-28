# Collect S3 Bucket events to CloudTrail

## Description
Collect events of a S3 Bucket to CloudTrail.

## Setup
1. Create a S3 Bucket
	1. Create Bucket: `aws s3 mb s3://s3-trail-bucket-49123`
	2. Update Bucket Policy: `aws s3api put-bucket-policy --bucket s3-trail-bucket-49123 --policy file://bucket-policy.json`
2. Create a Trail: 
	1. Create: `aws cloudtrail create-trail --name s3-trail --s3-bucket-name s3-trail-bucket-49123`
	2. Start logging: `aws cloudtrail start-logging --name s3-trail`
3. Perform action in the bucket: `echo abc | aws s3 cp - s3://s3-trail-bucket-49123/data.txt`
4. See logs (prefix `AWSLogs/523633434047/CloudTrail`): `aws s3 ls --recursive s3://s3-trail-bucket-49123`

## Cleanup
1. Delete Trail: `aws cloudtrail delete-trail --name s3-trail`
2. Delete S3 Bucket: `aws s3 rb --force s3://s3-trail-bucket-49123`
