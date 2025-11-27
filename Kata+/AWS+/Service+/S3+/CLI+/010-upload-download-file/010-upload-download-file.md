# 010-upload-download-file

## Task
Upload a local file to new S3 bucket and download it back.

## Steps
1. Set env vars
	```shell
	set -x
	export ORIGINAL_FILE=original.txt
	export DOWNLOADED_FILE=dowloaded.txt
	export BUCKET=kata-bucket-upload-download-file
	```
2. Create a bucket: `aws s3 mb s3://$BUCKET`
3. Create a file: `echo abc > /tmp/$ORIGINAL_FILE`
4. Upload the file: 
	1. Upload: `aws s3 cp /tmp/$ORIGINAL_FILE s3://$BUCKET/$ORIGINAL_FILE`
	2. Verify: `aws s3 ls s3://$BUCKET`
5. Download the file: `aws s3 cp s3://$BUCKET/$ORIGINAL_FILE /tmp/$DOWNLOADED_FILE`
6. Compare files: `diff /tmp/$ORIGINAL_FILE /tmp/$DOWNLOADED_FILE`

## Clean up
1. Delete the bucket: `aws s3 rb --force s3://$BUCKET`
2. Delete local files: `rm -f /tmp/$ORIGINAL_FILE /tmp/$DOWNLOADED_FILE`
2. Delete env variables: `set +x; unset ORIGINAL_FILE DOWNLOADED_FILE BUCKET`

## History
- 2025-11-16 success
