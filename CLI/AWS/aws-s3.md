# AWS s3 CLI (high-level)

List S3 buckets: `aws s3 ls`
Create a bucket: `aws s3 mb s3://iablokov471923643`
Upload a file to S3: `aws s3 cp my.txt s3://mybucket1/dir1/my.txt`
Delete a not-empty bucket: `aws s3 rb s3://iablokov471923643 --force`
