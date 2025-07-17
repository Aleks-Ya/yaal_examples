# AWS s3 CLI (high-level)

## Show
List S3 buckets: `aws s3 ls`
Print S3 file content to console: `aws s3 cp s3://03-grant-access-to-folder-for-user/dir1/data.txt -`

## Create
Create a bucket: `aws s3 mb s3://iablokov471923643`
Upload a file to S3: `aws s3 cp my.txt s3://mybucket1/dir1/my.txt`
Upload a string to S3: `echo abc | aws s3 cp - s3://mybucket1/dir1/my.txt`

## Delete
Delete an empty bucket: `aws s3 rb s3://iablokov471923643`
Delete a not-empty bucket: `aws s3 rb s3://iablokov471923643 --force`
Delete a file: `aws s3 rm s3://mybucket1/dir1/my.txt`
Delete a non-empty folder: `aws s3 rm --recursive s3://mybucket1/dir1/`
