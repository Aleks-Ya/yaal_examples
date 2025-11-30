# 040-ec2-to-ec2-connect-same-subnet

## Task
Create a VPC with a subnet. 
Create the 1st EC2 instance with server and the 2nd EC2 instance with client.
Connect both EC2 instances to the subnet.
Send an HTTP request from the client to the server.

## Steps
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
2. Create the Server
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
                1. HTTP
                    1. Type: `HTTP`
                    2. Source type: `Anywhere`
    	5. User data:
        	```bash
        	#!/bin/bash
        	echo "Hello from EC2 instance!" > index.html
        	sudo python3 -m http.server 80 &
        	```
3. Run web-server
    1. Connect to `instance-1` with Instance Connect
    2. Run server: `sudo python3 -m http.server 80`
3. Create the Client
4. Test

## Cleanup

## History
