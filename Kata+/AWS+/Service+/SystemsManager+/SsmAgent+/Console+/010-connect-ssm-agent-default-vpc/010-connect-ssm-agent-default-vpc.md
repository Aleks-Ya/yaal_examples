# 010-connect-ssm-agent-default-vpc

## Task
Connect to an SSM Agent withing an EC2 instance in the default VPC.

## Steps
1. Create an Instance Profile
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-connect-ssm-agent-default-vpc`
2. Create an EC2 instance
	1. Name: `kata-i-connect-ssm-agent-default-vpc`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `default`
		2. Subnet: `kata-subnet-connect-ssm-agent-default-vpc`
		3. Auto-assign public IP: `Enable`
		4. Firewall (security groups): `Select existing security group`
			1. Security group name: `default`
	6. Advanced details
		1. IAM instance profile: `kata-role-connect-ssm-agent-default-vpc`
3. Find the Instance in "Systems Manager" - "Explore nodes"

## Cleanup
1. Terminate Instance `kata-i-connect-ssm-agent-default-vpc`
2. Delete Role `kata-role-connect-ssm-agent-default-vpc`

## History
- 2025-10-08 success
