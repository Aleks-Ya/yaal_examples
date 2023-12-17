# 02-ec2-internet-connection

## Task

Create an EC2 instance with a web-server. Make the server available from the Internet.

## Setup

1. Create a VPC
    1. Resources to create: `VPC and more`
    2. Auto-generate: true, `project`
    3. IPv4 CIDR block: `10.0.0.0/28`
    4. IPv6 CIDR block: `No IPv6 CIDR block`
    5. Tenancy: `Default`
    6. Number of Availability Zones (AZs): `1`
    7. Number of public subnets: `1`
    8. Number of private subnets: `0`
    9. NAT gateways ($): `None`
    10. VPC endpoints: `None`
    11. DNS options
        1. Enable DNS hostnames: true
        2. Enable DNS resolution: true
2. Create a EC2 instance
    1. Name: `instance-1`
    2. AMI: `Amazon Linux`
    3. Instance type: `t2.micro`
    4. Key pair name: `Proceed without a keypair`
    5. Network settings
        1. VPC: `project-vpc`
        2. Subnet: `project-subnet-public1-us-east-1a`
        3. Auto-assign public IP: `Enabled`
        4. Firewall (security groups): `Create security group`
            1. Security group name: {generated}
            2. Description: {generated}
            3. Inbound Security Group Rules
                1. SSH
                    1. Type: `ssh`
                    2. Source type: `Anywhere`
                2. HTTP
                    1. Type: `HTTP`
                    2. Source type: `Anywhere`
3. Run web-server
    1. Connect to `instance-1` with Instance Connect
    2. Run server: `sudo python3 -m http.server 80`
4. Test web-server
    1. Copy "Public IPv4 address" from `instance-1`
    2. Test: `curl -i http://3.84.198.182`

## Cleanup

1. Terminate instance: `instance-1`
2. Delete VPC: `project-vpc`
