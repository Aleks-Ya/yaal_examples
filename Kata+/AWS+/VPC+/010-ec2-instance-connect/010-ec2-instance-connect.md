# 010-ec2-instance-internet-access

## Task
Create an EC2 instance connected to a new VPC and connect to it using Instance Connect.

Variants:
1. Use private connection (`Connect using EC2 Instance Connect Endpoint`)
2. Use public connection

## Private connection variant

### Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `vpc-1`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `vpc-1`
	2. Name: `subnet-1`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create a VPC Endpoint
	1. Name tag: `endpoint-1`
	2. Service category: `EC2 Instance Connect Endpoint`
	3. VPC: `vpc-1`
	4. Security groups: `default`
	5. Subnet: `subnet-1`
4. Create an EC2 instance
	1. Name: `instance-1`
	1. AMI: `Amazon Linux`
	2. Instance type: `t2.micro`
	3. Key pair name: `Proceed without a keypair`
	4. Network settings
		1. VPC: `vpc-1`
		2. Subnet: `subnet-1`
		3. Auto-assign public IP: `Disabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
5. Connect via Instance Connect
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using EC2 Instance Connect Endpoint`
	3. User name: `ec2-user`
	4. EC2 Instance Connect Endpoint: `endpoint-1`

### Cleanup
1. Terminate EC2 instance: `instance-1`
2. Delete VPC Endpoint: `endpoint-1`
3. Delete VPC: `vpc-1` (includes: Subnet and Security Group)


## Public connection variant

### Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `vpc-1`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `vpc-1`
	2. Name: `subnet-1`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Internet Gateway
	1. Name tag: `ig-1`
	2. Attach to VPC: `vpc-1`
4. Configure VPC
	1. Main route table
		1. Add route
			1. Destination: `0.0.0.0/0`
			2. Target: Internet Gateway `ig-1`
5. Create an EC2 instance
	1. Name: `instance-1`
	1. AMI: `Amazon Linux`
	2. Instance type: `t2.micro`
	3. Key pair name: `Proceed without a keypair`
	4. Network settings
		1. VPC: `vpc-1`
		2. Subnet: `subnet-1`
		3. Auto-assign public IP: `Enabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
5. Connect via Instance Connect
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using EC2 Instance Connect`
	3. User name: `ec2-user`

### Cleanup
1. Terminate EC2 instance: `instance-1`
2. Delete VPC: `vpc-1` (includes: Internet Gateway, Subnet, Security Group, Route Table)
