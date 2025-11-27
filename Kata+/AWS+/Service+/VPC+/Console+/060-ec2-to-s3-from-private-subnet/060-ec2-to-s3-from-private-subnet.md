# 060-ec2-to-s3-from-private-subnet

## Task
Connect an EC2 instance to S3 from a private subnet.

## Steps
1. Create a VPC
    1. Resources to create: `VPC only`
    2. Name: `kata-ec2-to-s3-from-private-subnet-vpc`
    3. IPv4 CIDR block: `IPv4 CIDR manual input`
    4. IPv4 CIDR: `10.0.0.0/28`
    5. IPv6 CIDR block: `No IPv6 CIDR block`
    6. Tenancy: `Default`
2. Create a Subnet
    1. VPC ID: `kata-ec2-to-s3-from-private-subnet-vpc`
    2. Name: `kata-ec2-to-s3-from-private-subnet-subnet`
    3. Availability Zone: `No preference`
    4. IPv4 VPC CIDR block: `10.0.0.0/28`
    5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create a Role for instance profile
    1. Trusted entity type: `AWS service`
    2. Use case: `EC2`
    3. Permissions policies: `AmazonS3ReadOnlyAccess`
    4. Role name: `kata-ec2-to-s3-from-private-subnet-role`
4. Create a EC2 instance
    1. Name: `kata-ec2-to-s3-from-private-subnet-instance`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
        1. VPC: `kata-ec2-to-s3-from-private-subnet-vpc`
        2. Subnet: `kata-ec2-to-s3-from-private-subnet-subnet`
        3. Auto-assign public IP: `Disabled`
        4. Firewall (security groups): `Create security group`
            1. Security group name: `kata-ec2-to-s3-from-private-subnet-security-group`
            2. Description: {generated}
            3. Inbound Security Group Rules
                1. Type: `ssh`
                2. Source type: `Anywhere`
    4. Instance profile: `kata-ec2-to-s3-from-private-subnet-role`
5. Create an VPC Endpoints ("PrivateLink and Lattice" -> "Endpoints")
    1. For Instance Connect
        1. Name tag: `kata-ec2-to-s3-from-private-subnet-instance-connect-endpoint`
        2. Service category: `EC2 Instance Connect Endpoint`
        3. VPC: `kata-ec2-to-s3-from-private-subnet-vpc`
        4. Security groups: `kata-ec2-to-s3-from-private-subnet-security-group`
        5. Subnet: `kata-ec2-to-s3-from-private-subnet-subnet`
    2. For S3
        1. Name tag: `kata-ec2-to-s3-from-private-subnet-s3-endpoint`
        2. Type: `AWS services`
        3. Services:
            1. Service Name: `com.amazonaws.us-east-1.s3`
            2. Type: `Gateway`
        4. Network settings:
            1. VPC: `kata-ec2-to-s3-from-private-subnet-vpc`
            2. Route tables: select 1 available
            3. Policy: `Full access`
6. Test access to S3
    1. Connect by Instance Connect
    2. Test: `aws s3 ls`

## Cleanup
1. Delete EC2 instance:
    1. Terminate instance: `kata-ec2-to-s3-from-private-subnet-instance`
    2. Delete role `kata-ec2-to-s3-from-private-subnet-role` 
2. Delete VPC:
    1. Delete VPC endpoint `kata-ec2-to-s3-from-private-subnet-s3-endpoint`
    2. Delete VPC endpoint `kata-ec2-to-s3-from-private-subnet-instance-connect-endpoint`
    2. Delete VPC `kata-ec2-to-s3-from-private-subnet-vpc`
