# 02-s3-from-public-subnet

## Task

Connect an EC2 instance to S3 from a public subnet.

!!!! NOT FINISHED

## Setup

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
3. Create a Role
1. Create a EC2 instance
    1. Name: `instance-1`
    2. Key pair name: `Proceed without a keypair`
    3. Network settings
        1. Allow HTTP traffic from the internet: enabled
    4. User data:
        ```bash
        #!/bin/bash
        echo "Hello from EC2 instance!" > index.html
        sudo python3 -m http.server 80 &
        ```
2. Test web-server
    1. Copy "Public IPv4 address" from `instance-1`
    2. Test: `curl -i http://50.19.33.2`

## Cleanup

1. Terminate instance: `instance-1`
