# 020-session-public

## Task
Connect to an EC2 instance in a public subnet by SessionManager from the Management Console.

## Steps
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-vpc-session-public`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `kata-vpc-session-public`
	2. Name: `kata-subnet-session-public`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Internet Gateway
	1. Name tag: `kata-igw-session-public`
	2. Attach to VPC: `kata-vpc-session-public`
4. Configure VPC
	1. Main route table
		1. Add route
			1. Destination: `0.0.0.0/0`
			2. Target: Internet Gateway `kata-igw-session-public`
5. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-session-public`
6. Create an EC2 instance
	1. Name: `kata-i-session-public`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-vpc-session-public`
		2. Subnet: `kata-subnet-session-public`
		3. Auto-assign public IP: `Enable`
		4. Firewall (security groups): `Create security group`
			1. Security group name: `kata-sg-session-public`
			2. Description: {generated}
			3. Inbound Security Group Rules: remove SSH
	6. Advanced details
		1. IAM instance profile: `kata-role-session-public`
7. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Clean
1. Terminate Instance `kata-i-session-public`
2. Delete Role `kata-role-session-public`
3. Delete VPC (includes Subnet, Security Group, Internet Gateway): `kata-vpc-session-public`

## History
- 2025-10-05 success
