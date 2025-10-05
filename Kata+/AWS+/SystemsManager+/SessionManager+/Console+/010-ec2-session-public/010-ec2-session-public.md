# 010-ec2-session-public

## Task
Connect to an EC2 instance in a public subnet by SessionManager from the Management Console.

## Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-vpc-ec2-session-public`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `kata-vpc-ec2-session-public`
	2. Name: `kata-subnet-ec2-session-public`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-ec2-session-public`
4. Create an EC2 instance
	1. Name: `kata-i-ec2-session-public`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-vpc-ec2-session-public`
		2. Subnet: `kata-subnet-ec2-session-public`
		3. Auto-assign public IP: `Disabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
	6. Advanced details
		1. IAM instance profile: `kata-role-ec2-session-public`
5. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`


## Clean
