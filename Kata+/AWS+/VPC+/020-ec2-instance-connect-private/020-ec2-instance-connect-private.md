# 020-ec2-instance-connect-private

## Task
Create an EC2 instance connected to a new VPC (private, `Connect using a Private IP`) 
and connect to it using Instance Connect.

## Setup
1. Create a VPC
	1. Resources to create: `VPC only`
	2. Name: `kata-ec2-instance-connect-private-vpc`
	3. IPv4 CIDR block: `IPv4 CIDR manual input`
	4. IPv4 CIDR: `10.0.0.0/28`
	5. IPv6 CIDR block: `No IPv6 CIDR block`
	6. Tenancy: `Default`
2. Create a Subnet
	1. VPC ID: `kata-ec2-instance-connect-private-vpc`
	2. Name: `kata-ec2-instance-connect-private-subnet`
	3. Availability Zone: `No preference`
	4. IPv4 VPC CIDR block: `10.0.0.0/28`
	5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create a VPC Endpoint ("PrivateLink and Lattice" -> "Endpoints")
	1. Name tag: `kata-ec2-instance-connect-private-endpoint`
	2. Service category: `EC2 Instance Connect Endpoint`
	3. VPC: `kata-ec2-instance-connect-private-vpc`
	4. Security groups: `default`
	5. Subnet: `kata-ec2-instance-connect-private-subnet`
4. Create an EC2 instance
	1. Name: `kata-ec2-instance-connect-private-instance`
	1. AMI: `Amazon Linux`
	2. Instance type: `t2.micro`
	3. Key pair name: `Proceed without a keypair`
	4. Network settings
		1. VPC: `kata-ec2-instance-connect-private-vpc`
		2. Subnet: `kata-ec2-instance-connect-private-subnet`
		3. Auto-assign public IP: `Disabled`
		4. Firewall (security groups): `Create security group`
			1. Security group name: {generated}
			2. Description: {generated}
			3. Inbound Security Group Rules
				1. Type: `ssh`
				2. Source type: `Anywhere`
5. Connect via Instance Connect
	1. Connect to instance: `EC2 Instance Connect`
	2. Connection Type: `Connect using a Private IP`
	3. EC2 Instance Connect Endpoint: `kata-ec2-instance-connect-private-endpoint` (wait for `Available` state)
	4. User name: `ec2-user`

## Cleanup
1. Terminate EC2 instance: `kata-ec2-instance-connect-private-instance`
2. Delete VPC Endpoint: `kata-ec2-instance-connect-private-endpoint`
3. Delete VPC: `kata-ec2-instance-connect-private-vpc` (includes: Subnet and Security Group)
