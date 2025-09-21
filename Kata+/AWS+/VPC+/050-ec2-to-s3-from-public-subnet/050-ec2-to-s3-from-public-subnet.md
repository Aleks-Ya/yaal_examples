# 050-ec2-to-s3-from-public-subnet

## Task
Connect an EC2 instance to S3 from a public subnet.

!!!! NOT FINISHED

## Setup
1. Create a VPC
    1. Resources to create: `VPC only`
    2. Name: `kata-ec2-to-s3-from-public-subnet-vpc`
    3. IPv4 CIDR block: `IPv4 CIDR manual input`
    4. IPv4 CIDR: `10.0.0.0/28`
    5. IPv6 CIDR block: `No IPv6 CIDR block`
    6. Tenancy: `Default`
2. Create a Subnet
    1. VPC ID: `kata-ec2-to-s3-from-public-subnet-vpc`
    2. Name: `kata-ec2-to-s3-from-public-subnet-subnet`
    3. Availability Zone: `No preference`
    4. IPv4 VPC CIDR block: `10.0.0.0/28`
    5. IPv4 subnet CIDR block: `10.0.0.0/28`
3. Create an Internet Gateway
    1. Name tag: `kata-ec2-to-s3-from-public-subnet-ig`
    2. Attach to VPC: `kata-ec2-to-s3-from-public-subnet-vpc`
4. Configure VPC
    1. Main route table
        1. Add route
            1. Destination: `0.0.0.0/0`
            2. Target: Internet Gateway `kata-ec2-to-s3-from-public-subnet-ig`
3. Create an S3 bucket
    1. Bucket type: `General purpose`
    2. Bucket name: `kata-ec2-to-s3-from-public-subnet-bucket`
    3. Block all public access: enabled
4. Create a Role for instance profile
    1. Trusted entity type: `AWS service`
    2. Use case: `EC2`
    3. Permissions policies: `AmazonS3FullAccess`
    4. Role name: `kata-ec2-to-s3-from-public-subnet-role`
1. Create a EC2 instance
    1. Name: `kata-ec2-to-s3-from-public-subnet-instance`
    2. Key pair name: `Proceed without a keypair`
    3. (?) Instance profile: `kata-ec2-to-s3-from-public-subnet-role`
    3. Network settings
        1. VPC: `kata-ec2-to-s3-from-public-subnet-vpc`
        2. Subnet: `kata-ec2-to-s3-from-public-subnet-subnet`
        3. Auto-assign public IP: `Enabled`
        4. Security group: 
            1. Allow SSH traffic from Anywhere
            5. Allow HTTP traffic from the internet
    4. User data:
        ```bash
        #!/bin/bash
        echo "Hello from EC2 instance!" > index.html
        sudo python3 -m http.server 80 &
        ```
2. Test web-server
    1. Copy "Public IPv4 address" from `kata-ec2-to-s3-from-public-subnet-instance`
    2. Test: `curl -i http://50.19.33.2`

## Cleanup
1. Terminate instance: `kata-ec2-to-s3-from-public-subnet-instance`
