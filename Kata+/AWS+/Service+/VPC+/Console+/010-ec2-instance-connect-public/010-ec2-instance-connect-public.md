# 010-ec2-instance-connect-public

## Task
Create an EC2 instance connected to a new VPC (public, `Connect using a Public IP`) and connect to it using Instance Connect.

## Steps
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-ec2-instance-connect-public-vpc`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `kata-ec2-instance-connect-public-vpc`
	2. Name: `kata-ec2-instance-connect-public-subnet`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Internet Gateway
	1. Name tag: `kata-ec2-instance-connect-public-ig`
	2. Attach to VPC: `kata-ec2-instance-connect-public-vpc`
4. Configure VPC
	1. Main route table
		1. Add route
			1. Destination: `0.0.0.0/0`
			2. Target: Internet Gateway `kata-ec2-instance-connect-public-ig`
5. Create an EC2 instance
	1. Name: `kata-ec2-instance-connect-public-instance`
	2. AMI: `Amazon Linux`
	3. Instance type: `t2.micro`
	4. Key pair name: `Proceed without a keypair`
	5. Network settings
		1. VPC: `kata-ec2-instance-connect-public-vpc`
		2. Subnet: `kata-ec2-instance-connect-public-subnet`
		3. Auto-assign public IP: `Enabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
6. Connect via Instance Connect
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Public IP`
	3. User name: `ec2-user`

## Cleanup
1. Terminate EC2 instance: `kata-ec2-instance-connect-public-instance`
2. Delete VPC: `kata-ec2-instance-connect-public-vpc` (includes: Internet Gateway, Subnet, Security Group, Route Table)

## History
