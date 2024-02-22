# 01-create-s3-bucket

## Task
Create a S3 bucket using CloudFormation.

## Setup
By AWS Console.
1. Create stack, with new resources (standard)
2. Prepare template: `Create template in Designer`
	1. Resource types -> S3 -> Bucket
	2. Add property `"BucketName": "tmp-bucket-cloud-formation"`
	3. Click `Create stack`
3. Stack name: `stack-1`
4. Click Submit

## Cleanup
1. Delete the template.
