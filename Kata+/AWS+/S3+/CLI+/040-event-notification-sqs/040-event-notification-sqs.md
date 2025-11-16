# 040-event-notification-sqs

## Task
Create an Event Notification on an S3 Bucket that sends notifications to an SQS Queue.

## Steps
1. Change current dir
2. Set env vars
	```shell
	set -x
	export QUEUE=kata-q-event-notification-sqs
	export BUCKET=kata-bucket-event-notification-sqs
	```
2. Create queue
	1. Create queue: `aws sqs create-queue --queue-name $QUEUE`
	2. Set Queue Policy: 
		```shell 
		aws sqs set-queue-attributes --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/$QUEUE \
			--attributes Policy="'$(jq -c . queue-policy.json)'"
		```
3. Create bucket
	1. Create bucket: `aws s3 mb s3://$BUCKET`
	2. Create an Event Notification: 
		```shell
		aws s3api put-bucket-notification-configuration --bucket $BUCKET \
			--notification-configuration file://event-notifications-config.json
		```
4. Test
	1. Upload a file: `echo abc | aws s3 cp - s3://$BUCKET/file1.txt`
	2. Receive messages: `aws sqs receive-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/$QUEUE`


## Cleanup
1. Delete Bucket: `aws s3 rb --force s3://$BUCKET`
2. Delete Queue: `aws sqs delete-queue --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/$QUEUE`
3. Delete env variables: `set +x; unset QUEUE BUCKET`

## History
- 2025-11-16 success
