# 020-connect-ssm-agent-public

## Task
Status: not finished
Create an EC2 Instance with installed SSM Agent in a public subnet.

## Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-vpc-connect-ssm-agent-public`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `kata-vpc-connect-ssm-agent-public`
	2. Name: `kata-subnet-connect-ssm-agent-public`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-connect-ssm-agent-public`
4. Create an EC2 instance
	1. Name: `kata-i-connect-ssm-agent-public`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-vpc-connect-ssm-agent-public`
		2. Subnet: `kata-subnet-connect-ssm-agent-public`
		3. Auto-assign public IP: `Disabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
	6. Advanced details
		1. IAM instance profile: `kata-role-connect-ssm-agent-public`
5. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Cleanup
