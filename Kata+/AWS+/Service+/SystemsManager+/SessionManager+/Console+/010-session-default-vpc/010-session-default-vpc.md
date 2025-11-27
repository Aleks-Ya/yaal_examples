# 010-session-default-vpc

## Task
Status: success
Connect to an EC2 instance by SessionManager in the default VPC.

## Steps
1. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-session-default-vpc`
2. Create an EC2 instance
	1. Name: `kata-i-session-default-vpc`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `default`
		2. Subnet: `kata-subnet-session-default-vpc`
		3. Auto-assign public IP: `Enable`
		4. Firewall (security groups): `Select existing security group`
			1. Security group name: `default`
	6. Advanced details
		1. IAM instance profile: `kata-role-session-default-vpc`
3. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Cleanup
1. Terminate Instance `kata-i-session-default-vpc`
2. Delete Role `kata-role-session-default-vpc`

## History
- 2025-10-08 success
