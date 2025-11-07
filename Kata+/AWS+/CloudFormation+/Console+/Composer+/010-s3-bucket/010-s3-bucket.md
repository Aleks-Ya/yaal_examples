# 010-s3-bucket

## Task
Use CloudFormation to create an S3 bucket with the Infrastructure Composer.

## Steps
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Drag `Resources`-`AWS::S3::Bucket`
		2. Override default bucket name: enabled
		3. Bucket name: `kata-bucket-s3-bucket`
		4. Create
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-s3-bucket-transfer-bucket`
3. Stack name: `kata-stack-s3-bucket`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-s3-bucket`
2. Delete Transfer Bucket `kata-bucket-s3-bucket-transfer-bucket`

## History
