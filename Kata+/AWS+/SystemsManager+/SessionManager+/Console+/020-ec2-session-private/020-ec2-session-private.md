# 020-ec2-session-private

## Task
Status: success
Connect to an EC2 instance in a private subnet by SessionManager from the Management Console.

## TODO
- [ ] Try lower access in the Security Group (`Outbound rules: all traffic to anywhere`)
- [ ] Do I need all 4 VPC endpoints?
      https://repost.aws/knowledge-center/ec2-systems-manager-vpc-endpoints
      https://repost.aws/knowledge-center/systems-manager-ec2-instance-not-appear

## Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-vpc-ec2-session-private`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
	7. DNS resolution: `Enabled`
	8. DNS hostnames: `Enabled`
2. Create a Subnet
	1. VPC ID: `kata-vpc-ec2-session-private`
	2. Name: `kata-subnet-ec2-session-private`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `EC2`
	3. Use case: `EC2 Role for AWS Systems Manager`
	4. Permissions policies: `AmazonSSMManagedInstanceCore`
	5. Role name: `kata-role-ec2-session-private`
4. Create a Security Group
	1. Security group name: `kata-sg-ec2-session-private`
	2. Description: `kata-sg-ec2-session-private`
	3. VPC: `kata-vpc-ec2-session-private`
	4. Inbound rules: none
	5. Outbound rules: all traffic to anywhere
5. Create VPC endpoints
	1. SSM
		1. Name tag: `kata-endpoint-ec2-session-private-ssm`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ssm`
		4. VPC: `kata-vpc-ec2-session-private`
		5. Subnets: `kata-subnet-ec2-session-private`
		6. Security Group: `kata-sg-ec2-session-private`
	2. EC2 Messages
		1. Name tag: `kata-endpoint-ec2-session-private-ec2messages`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ec2messages`
		4. VPC: `kata-vpc-ec2-session-private`
		5. Subnets: `kata-subnet-ec2-session-private`
		6. Security Group: `kata-sg-ec2-session-private`
	3. EC2
		1. Name tag: `kata-endpoint-ec2-session-private-ec2`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ec2`
		4. VPC: `kata-vpc-ec2-session-private`
		5. Subnets: `kata-subnet-ec2-session-private`
		6. Security Group: `kata-sg-ec2-session-private`
	3. SSM Messages
		1. Name tag: `kata-endpoint-ec2-session-private-ssmmessages`
		2. Type: `AWS services`
		3. Services: `com.amazonaws.us-east-1.ssmmessages`
		4. VPC: `kata-vpc-ec2-session-private`
		5. Subnets: `kata-subnet-ec2-session-private`
		6. Security Group: `kata-sg-ec2-session-private`
6. Create an EC2 instance
	1. Name: `kata-i-ec2-session-private`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-vpc-ec2-session-private`
		2. Subnet: `kata-subnet-ec2-session-private`
		3. Auto-assign public IP: `Enabled`
		4. Firewall (security groups): `Select existing security group`
			1. Common security groups: `kata-sg-ec2-session-private`
	6. Advanced details
		1. IAM instance profile: `kata-role-ec2-session-private`
7. Connect via Session Manager
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Clean
1. Terminate Instance `kata-i-ec2-session-private`
2. Delete Role `kata-role-ec2-session-private`
3. Delete VPC:
	1. Delete VPC endpoints
	2. Delete VPC (includes Subnet, Security Group, Internet Gateway): `kata-vpc-ec2-session-private`

## History
- 2025-10-05 success
