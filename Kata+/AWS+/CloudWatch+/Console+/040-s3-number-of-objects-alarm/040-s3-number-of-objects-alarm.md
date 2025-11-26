# 040-s3-number-of-objects-alarm

## Task
Create an CloudWatch metric alarm that monitors number of objects in an S3 bucket.

## Steps
1. Create an S3 bucket:
	1. Bucket type: `General purpose`
	2. Bucket name: `kata-bucket-s3-number-of-objects-alarm`
2. Create an Alarm:
	1. !!! S3 metrics are delivered once a day

## Cleanup
1. Delete bucket `kata-bucket-s3-number-of-objects-alarm`

## History
- 2025-11-26 fail
