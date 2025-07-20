# AWS s3api CLI (low-level)

List S3 buckets: `aws s3api list-buckets`
List objects in a S3 bucket: `aws s3api list-objects --bucket qwasceitnsuryw7`
List objects by S3 URL: `aws s3 ls s3://yaal-backup/duplicity-backup-docs-vault/`
Show object info: `aws s3api head-object --bucket qwasceitnsuryw7 --key my1/bye.txt`
Download file from S3: `aws s3api get-object --bucket qwasceitnsuryw7 --key my1/bye.txt /tmp/bye.txt`
Delete S3 object: `aws s3api delete-object --bucket qwasceitnsuryw7 --key my1/`
Upload a file to S3:
```
aws s3api put-object --bucket yaal-test-bucket --key my_texts/bye.txt --body /tmp/bye.txt
#Link to the object: https://yaal-test-bucket.s3.eu-central-1.amazonaws.com/my_texts/bye.txt
```

## Bucket Policy
Show Bucket Policy: `aws s3api get-bucket-policy --bucket bucket1`
Update the Bucket Policy: `aws s3api put-bucket-policy --bucket alex-717183y1471234 --policy file://bucket-policy.json`
Delete Bucket Policy: `aws s3api delete-bucket-policy --bucket bucket1`
