# 030-template-parameter

## Task
Status: ?
Use CloudFormation to create an S3 Bucket with the Infrastructure Composer.
Bucket name should be defined as a Template Parameter.

## Steps
1. Create stack
	1. Prepare template: `Choose an existing template`
	2. Template source: `Upload a template file`
	3. Upload a template file: `template.yaml`
4. Stack name: `kata-stack-template-parameter`
5. Parameters
	1. MagicNumber: `3`
	2. TheBucketName: `kata-bucket-template-parameter`
6. Click Submit

## Cleanup
1. Delete Stack `kata-stack-template-parameter`
2. Delete Transfer Bucket `cf-templates-*`
