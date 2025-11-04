# 070-ec2-internet-gateway

NOT FINISHED!!!

## Task
Create an EC2 Instance with a web-server in a private subnet. 
Make the web-server reachible from the Internet using an Internet Gateway.

## Steps
1. Create a VPC
    1. Resources to create: `VPC only`
    2. Name: `kata-vpc-ec2-internet-gateway`
    3. IPv4 CIDR block: `IPv4 CIDR manual input`
    4. IPv4 CIDR: `10.0.0.0/28`
    5. IPv6 CIDR block: `No IPv6 CIDR block`
    6. Tenancy: `Default`
2. Create a Subnet
    1. VPC ID: `kata-vpc-ec2-internet-gateway`
    2. Name: `kata-subnet-ec2-internet-gateway`
    3. Availability Zone: `No preference`
    4. IPv4 VPC CIDR block: `10.0.0.0/28`
    5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Internet Gateway
	1. Name tag: `kata-igw-ec2-internet-gateway`
	2. Attach to VPC: `kata-vpc-ec2-internet-gateway`
4. Configure VPC
	1. Main route table
		1. Add route
			1. Destination: `0.0.0.0/0`
			2. Target: Internet Gateway `kata-igw-ec2-internet-gateway`
5. Create a EC2 instance
    1. Name: `kata-i-ec2-internet-gateway`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
        1. VPC: `kata-vpc-ec2-internet-gateway`
        2. Subnet: `kata-subnet-ec2-internet-gateway`
        3. Auto-assign public IP: `Disabled`
        4. Firewall (security groups): `Create security group`
            1. Security group name: `kata-sg-ec2-internet-gateway`
            2. Description: {generated}
            3. Inbound Security Group Rules
            	1. SSH
                	1. Type: `ssh`
                	2. Source type: `Anywhere`
                 1. HTTP
                    1. Type: `HTTP`
                    2. Source type: `Anywhere`
    6. User data:
        ```bash
        #!/bin/bash
        echo "Hello from EC2 instance!" > index.html
        sudo python3 -m http.server 80 &
        ```
6. Test: `curl http://`

## Clean
1. Terminate instance
2. Delete endpoint
3. Delete VPC
