# Event Notifications to an SQS queue

## Task
Create an Event Notification on an S3 Bucket that sends notifications to an SQS Queue.

## Setup
1. Change the current dir
2. Create queue
	1. Create queue: `aws sqs create-queue --queue-name queue1`
	2. Set Queue Policy: 
	```shell 
	aws sqs set-queue-attributes --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1 \
	--attributes Policy="'$(jq -c . queue-policy.json)'"
	```
3. Create bucket
	1. Create bucket: `aws s3 mb s3://tmp-kata-060-event-notification-sqs`
	2. Configure Event Notifications: 
	```shell
	aws s3api put-bucket-notification-configuration --bucket tmp-kata-060-event-notification-sqs \
	--notification-configuration file://event-notifications-config.json
	```
4. Test
	1. Upload a file: `echo abc | aws s3 cp - s3://tmp-kata-060-event-notification-sqs/file1.txt`
	2. Receive messages: `aws sqs receive-message --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1`


## Cleanup
1. Delete S3 Bucket: `aws s3 rb --force s3://tmp-kata-060-event-notification-sqs`
2. Delete Queue: `aws sqs delete-queue --queue-url https://sqs.us-east-1.amazonaws.com/523633434047/queue1`
