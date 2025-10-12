# 030-ecr-image-to-ec2

## Task
Status: not finished
Deploy an Docker Image from ECR Repository to an EC2 Instance.

## Setup
1. Create an ECR Repository
	1. Repository name: `kata-ns-ecr-image-to-ec2/kata-repo-ecr-image-to-ec2`
1. Create an EC2 Instance
3. Create a CodeDeploy Application
	1. Create a Service Role
		1. Trusted entity type: `AWS Service`
		2. Service or use case: `CodeDeploy`
		3. Use case: `CodeDeploy`
		4. Permissions policies: `AWSCodeDeployRole`
		5. Role name: `kata-role-ecr-image-to-ec2`
	2. Create an Application:
		1. Application name: `kata-app-ecr-image-to-ec2`
		2. Compute platform: `EC2/On-premises`
	3. Create a Deployment Group:
		1. Enter a deployment group name: `kata-dg-ecr-image-to-ec2`
		2. Enter a service role: `kata-role-ecr-image-to-ec2`
		3. Deployment type: `In-place`
		4. Environment configuration
			1. Amazon EC2 instances
				1. Tag group 1
					1. Key: `kata-tag-ecr-image-to-ec2`
					2. Value: empty
		5. Agent configuration with AWS Systems Manager
			1. Install AWS CodeDeploy Agent: `Only once`
		6. Deployment settings: `CodeDeployDefault.AllAtOnce`
		7. Load balancer
			1. Enable load balancing: `false`
	4. Create a Deployment
		1. Application: `kata-app-ecr-image-to-ec2`
		2. Deployment Group: `kata-dg-ecr-image-to-ec2`
		3. 

## Cleanup

## History

