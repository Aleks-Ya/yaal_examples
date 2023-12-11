# 001-ec2-instance-internet-access

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
	1. Name: `subnet-1`
	2. Availability Zone: `No preference`
	3. IPv4 VPC CIDR block: `10.0.0.0/28`
	4. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create a VPC Endpoint
	1. Name tag: `endpoint-1`
	2. Service category: `EC2 Instance Connect Endpoint`
	3. VPC: `vpc-1`
	4. Security groups: `default`
	5. Subnet: `subnet-1`
4. Create an EC2 instance
	1. AMI: `Amazon Linux`
	2. Instance type: `t2.micro`
	3. Key pair name: `Proceed without a keypair`
	4. VPC: `vpc-1`
	5. Subnet: `subnet-1`
	6. Auto-assign public IP: `Disabled`
	7. Firewall (security groups): `Create security group`
	8. Security group name: {generated}
	9. Description: {generated}
	10. Inbound Security Group Rules
		1. Type: `ssh`
		2. Source type: `Anywhere`
5. Connect via Instance Connect
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using EC2 Instance Connect Endpoint`
	3. EC2 Instance Connect Endpoint: `endpoint-1`

### Cleanup
1. Terminate EC2 instance
2. Delete VPC Endpoint
3. Delete VPC (includes Subnet and Security Groupes)
