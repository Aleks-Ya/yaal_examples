# 030-docker-to-ec2


## Task
Status: success
Deploy Docker container with Caddy server in an EC2 Instance.

## Steps
1. Create a EC2 instance
	1. Create Instance Profile
		1. Trusted entity type: `AWS service`
		2. Service or use case: `EC2`
		3. Use case: `EC2 Role for AWS Systems Manager`
		4. Permissions policies: 
			1. `AmazonSSMManagedInstanceCore`
			2. Create inline policy
				1. Statement 1
					1. Service: `S3`
					2. Actions allowed: `ListBucket`
					3. Resources: `arn:aws:s3:::kata-bucket-docker-to-ec2`
				2. Statement 2
					1. Service: `S3`
					2. Actions allowed: `GetObject`
					3. Resources: `arn:aws:s3:::kata-bucket-docker-to-ec2/application-revision.tar`
			3. Policy name: `kata-policy-docker-to-ec2`
		5. Role name: `kata-role-docker-to-ec2-service-instance`
    2. Create Instance
    	1. Name: `kata-i-docker-to-ec2`
        2. Tags
			1. Key: `kata-tag-docker-to-ec2`
			2. Value: empty
    	3. Key pair name: `Proceed without a keypair`
    	4. Network settings
        	1. VPC: `default`
        	2. Auto-assign public IP: `enabled`
        	3. Security group: `default`
    	5. Instance Profile: `kata-role-docker-to-ec2-service-instance`
    	6. User data:
        	```bash
			#!/bin/bash
			dnf update -y
			dnf install docker -y
			systemctl start docker
			systemctl enable docker
			usermod -a -G docker ec2-user
        	```
	3. Test SSM Agent: find the Instance in "Explore nodes"
2. Create a CodeDeploy Application
	1. Create an Application Revision
		1. Create an S3 bucket
			1. Bucket type: `General purpose`
			2. Bucket name: `kata-bucket-docker-to-ec2`
			3. Block all public access: `enabled`
		2. Create an Application Revision
			1. Create an archive: `tar -cf application-revision.tar -C application-revision .`
			2. Verify the archive: `tar -tf application-revision.tar`
			3. Upload file `application-revision.tar` to bucket `kata-bucket-docker-to-ec2`
	2. Create a Service Role
		1. Trusted entity type: `AWS Service`
		2. Service or use case: `CodeDeploy`
		3. Use case: `CodeDeploy`
		4. Permissions policies: `AWSCodeDeployRole`
		5. Role name: `kata-role-docker-to-ec2-service`
	2. Create an Application:
		1. Application name: `kata-app-docker-to-ec2`
		2. Compute platform: `EC2/On-premises`
	3. Create a Deployment Group:
		1. Enter a deployment group name: `kata-dg-docker-to-ec2`
		2. Enter a service role: `kata-role-docker-to-ec2-service`
		3. Deployment type: `In-place`
		4. Environment configuration
			1. Amazon EC2 instances
				1. Tag group 1
					1. Key: `kata-tag-docker-to-ec2`
					2. Value: empty
		5. Agent configuration with AWS Systems Manager
			1. Install AWS CodeDeploy Agent: `Only once`
		6. Deployment settings: `CodeDeployDefault.AllAtOnce`
		7. Load balancer
			1. Enable load balancing: `false`
	4. Create a Deployment
		1. Application: `kata-app-docker-to-ec2`
		2. Deployment Group: `kata-dg-docker-to-ec2`
		3. Revision location: `s3://kata-bucket-docker-to-ec2/application-revision.tar`
		4. Revision file type: `.tar`
3. Test in Browser: http://107.21.68.114

## Cleanup
1. Teminate Instance `kata-i-docker-to-ec2`
2. Delete Application `kata-app-docker-to-ec2`
3. Delete Bucket `kata-bucket-docker-to-ec2`
4. Delete Roles `kata-role-docker-to-ec2-service-instance` and `kata-role-docker-to-ec2-service`

## History
- 2025-10-13 success
