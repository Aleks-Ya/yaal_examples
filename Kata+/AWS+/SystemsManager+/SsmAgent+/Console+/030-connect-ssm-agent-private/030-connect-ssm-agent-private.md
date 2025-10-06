# 030-connect-ssm-agent-private

## Task
Status: not finished
Create an EC2 Instance with installed SSM Agent in a private subnet.

## Setup
1. Create a VPC
	1. Create:
		1. Resources to create: `VPC only`
		2. Name: `kata-vpc-connect-ssm-agent-private`
		3. IPv4 CIDR block: `IPv4 CIDR manual input`
		4. IPv4 CIDR: `10.0.0.0/28`
		5. IPv6 CIDR block: `No IPv6 CIDR block`
		6. Tenancy: `Default`
	2. Enable hostnames:
		1. DNS resolution: `Enabled`
		2. DNS hostnames: `Enabled`
2. Create a Subnet
	1. VPC ID: `kata-vpc-connect-ssm-agent-private`
	2. Name: `kata-subnet-connect-ssm-agent-private`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-connect-ssm-agent-private`
4. Create a Security Group
	1. Security group name: `kata-sg-connect-ssm-agent-private`
	2. Description: `kata-sg-connect-ssm-agent-private`
	3. VPC: `kata-vpc-connect-ssm-agent-private`
	4. Inbound rules: allow HTTPS from anywhere
	5. Outbound rules: allow HTTPS to anywhere
5. Create VPC endpoints
	1. SSM
		1. Name tag: `kata-endpoint-connect-ssm-agent-private-ssm`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ssm`
		4. VPC: `kata-vpc-connect-ssm-agent-private`
		5. Subnets: `kata-subnet-connect-ssm-agent-private`
		6. Security Group: `kata-sg-connect-ssm-agent-private`
	2. EC2 Messages
		1. Name tag: `kata-endpoint-connect-ssm-agent-private-ec2messages`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ec2messages`
		4. VPC: `kata-vpc-connect-ssm-agent-private`
		5. Subnets: `kata-subnet-connect-ssm-agent-private`
		6. Security Group: `kata-sg-connect-ssm-agent-private`
	3. EC2
		1. Name tag: `kata-endpoint-connect-ssm-agent-private-ec2`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ec2`
		4. VPC: `kata-vpc-connect-ssm-agent-private`
		5. Subnets: `kata-subnet-connect-ssm-agent-private`
		6. Security Group: `kata-sg-connect-ssm-agent-private`
	3. SSM Messages
		1. Name tag: `kata-endpoint-connect-ssm-agent-private-ssmmessages`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ssmmessages`
		4. VPC: `kata-vpc-connect-ssm-agent-private`
		5. Subnets: `kata-subnet-connect-ssm-agent-private`
		6. Security Group: `kata-sg-connect-ssm-agent-private`
6. Create an EC2 instance
	1. Name: `kata-i-connect-ssm-agent-private`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-vpc-connect-ssm-agent-private`
		2. Subnet: `kata-subnet-connect-ssm-agent-private`
		3. Auto-assign public IP: `Disabled`
		4. Firewall (security groups): `Select existing security group`
			1. Common security groups: `kata-sg-connect-ssm-agent-private`
	6. Advanced details
		1. IAM instance profile: `kata-role-connect-ssm-agent-private`
7. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Clean
1. Terminate Instance `kata-i-connect-ssm-agent-private`
2. Delete Role `kata-role-connect-ssm-agent-private`
3. Delete VPC:
	1. Delete VPC endpoints
	2. Delete VPC (includes Subnet, Security Group, Internet Gateway): `kata-vpc-connect-ssm-agent-private`

## History

