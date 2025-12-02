# 010-linux-python

## Task
Create a Linux AMI with Python.

## Steps
1. Create a Recipe
	1. Name: `kata-recipe-linux-python`
	2. Base image: 
		1. Select image: `Select managed images`
		2. Image Operating System: `Amazon Linux`
		3. Image origin: `Quick start`
		4. Image name: `Amazon Linux 2023 x86`
2. Create an Infrastructure Configuration
	1. Create an IAM Role:
		1. Trusted entity type: `AWS service`
		2. Service or use case: `EC2`
		3. Use case: `EC2`
		4. Permissions policies:
			1. `EC2InstanceProfileForImageBuilder`
			2. `EC2InstanceProfileForImageBuilderECRContainerBuilds`
			3. `AmazonSSMManagedInstanceCore`
		5. Role name: `kata-role-linux-python`
	2. Create an Infrastructure Configuration:
		1. Name: `kata-infra-linux-python`
		2. IAM role: `kata-role-linux-python`
		3. Instance type: `t3.small`
3. Create a Distribution Settings
	1. Output type: `Amazon Machine Image (AMI)`
	2. Name: `kata-distribution-linux-python`
4. Create an Image Pipeline
	1. Pipeline name: `kata-pipeline-linux-python`
	2. Enhanced metadata collection: false
	3. Build schedule: `Manual`
	4. Choose recipe
		1. Configuration options: `Use existing recipe`
		2. Recipe name: `kata-recipe-linux-python`
	5. Define infrastructure configuration: 
		1. Configuration options: `Use existing infrastructure configuration`
		2. Configuration name: `kata-infra-linux-python`
	6. Define distribution settings: 
		1. Configuration options: `Use existing distribution settings`
		2. Configuration name: `kata-distribution-linux-python`

## Cleanup
1. Delete the Recipe
2. Delete the Infrastructure Configuration
3. Delete the Distribution Settings
4. Delete the Image Pipeline
5. Delete the Image
6. (?) Delete role `EC2InstanceProfileForImageBuilder`
7. Delete CloudWatch Log Groups:
	1. `/aws/imagebuilder/kata-recipe-linux-python`
	2. `/aws/imagebuilder/pipeline/kata-pipeline-linux-python`

## History
- 2025-12-03 success