# 030-template-parameter

## Task
Use CloudFormation to create an S3 Bucket with the Infrastructure Composer.
Bucket name should be defined as a Template Parameter.

## Steps
1. Create stack
	1. Prepare template: `Choose an existing template`
	2. Template source: `Upload a template file`
	3. Upload a template file: `template.yaml`
2. Stack name: `kata-stack-template-parameter`
3. Parameters
	1. MagicNumber: `3`
	2. TheBucketName: `kata-bucket-template-parameter`
4. Click Submit
5. Verify: exists bucket `kata-bucket-template-parameter` with tag `Lucky number` = `3`

## Cleanup
1. Delete Stack `kata-stack-template-parameter`
2. Delete Transfer Bucket `cf-templates-*`

## History
- 2025-11-08 success
