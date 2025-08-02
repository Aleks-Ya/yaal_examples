# AWS s3 CLI (high-level)

## Show
List S3 buckets: `aws s3 ls`
Print S3 file content to console: `aws s3 cp s3://03-grant-access-to-folder-for-user/dir1/data.txt -`

## Create
Create a bucket: `aws s3 mb s3://iablokov471923643`
Upload a file to S3: `aws s3 cp my.txt s3://mybucket1/dir1/my.txt`
Upload a string to S3: `echo abc | aws s3 cp - s3://mybucket1/dir1/my.txt`

## Copy
Copy an object with SSE-KMS encryption: 
```shell
aws s3 cp --sse aws:kms --sse-kms-key-id 124d75a1-adfe-477c-b2d7-af1df62a77cc s3://bucket1/data.txt s3://bucket2
```

## Delete
Delete an empty bucket: `aws s3 rb s3://iablokov471923643`
Delete a not-empty bucket: `aws s3 rb --force s3://iablokov471923643`
Delete a file: `aws s3 rm s3://mybucket1/dir1/my.txt`
Delete a non-empty folder: `aws s3 rm --recursive s3://mybucket1/dir1/`

## Website
Start/update the website: `aws s3 website s3://bucket1 --index-document index.html`

## Public access block
Check: `aws s3api get-public-access-block --bucket bucket1`

## Sync
Copy files from local dir to S3 folder: `aws s3 sync ~/tmp/for_sync s3://kata-shared1/synced/`
