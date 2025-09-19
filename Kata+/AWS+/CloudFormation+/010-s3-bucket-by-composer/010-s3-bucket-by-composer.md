# 010-s3-bucket-by-composer

## Task
Use CloudFormation to create an S3 bucket with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Drag `Resources`-`S3 Bucket`
		2. Override default bucket name: enabled
		3. Bucket name: `kata-s3-bucket-by-composer-bucket`
		4. Create
			1. Click `Create template`
			2. Transfer bucket name: `kata-s3-bucket-by-composer-transfer-bucket`
3. Stack name: `kata-s3-bucket-by-composer-stack`
4. Click Submit

## Cleanup
1. Delete Stack `kata-s3-bucket-by-composer-stack`
2. Delete Transfer Bucket `kata-s3-bucket-by-composer-transfer-bucket`
