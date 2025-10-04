# 010-user-data

NOT FINISHED!!!

## Task
Management interface: AWS CLI
Create a EC2 instance with a web-server which should start automatically.

## Setup
1. Create a VPC
    1. Create VPC:
    ```shell 
    aws ec2 create-vpc \
        --cidr-block 10.0.0.0/28 \
        --tag-specifications 'ResourceType=vpc,Tags=[{Key=Name,Value="kata-vpc-user-data"}]'
    ```
    2. Get VPC ID: 
    ```shell
    export VPC=$(aws ec2 describe-vpcs \
        --query "Vpcs[*].VpcId" --output text \
        --filters "Name=tag:Name,Values=kata-vpc-user-data")
    ```
2. Creaet a subnet
    1. Create Subnet:
    ```shell
    aws ec2 create-subnet --cidr-block 10.0.0.0/28 --vpc-id $VPC \
        --tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=kata-subnet-user-data}]'
    ```
    2. Get Subnet ID:
    ```shell
    export SUBNET=$(aws ec2 describe-subnets --query "Subnets[*].SubnetId" --output text \
          --filters "Name=tag:Name,Values=kata-subnet-user-data")
    ```
3. Create an Internet Gateway
    1. Create:
    ```shell
    aws ec2 create-internet-gateway \
        --tag-specifications 'ResourceType=internet-gateway,Tags=[{Key=Name,Value="kata-igw-user-data"}]'
    ```
    2. Get ID:
    ```shell
    export IGW=$(aws ec2 describe-internet-gateways --query "InternetGateways[0].InternetGatewayId" --output text \
      --filters "Name=tag:Name,Values=kata-igw-user-data")
    ```
    3. Attach an Internet Gateway to a VPC: `aws ec2 attach-internet-gateway --internet-gateway-id $IGW --vpc-id $VPC`
4. Configure Route Table
    1. Get Route Table ID
    ```shell
    export RTB=$(aws ec2 describe-route-tables --query "RouteTables[*].RouteTableId" --output text \
        --filters "Name=vpc-id,Values=$VPC")
    ```
    2. Add route to Internet Gateway
    ```shell
    aws ec2 create-route --destination-cidr-block 0.0.0.0/0 --route-table-id $RTB --gateway-id $IGW
    ```
5. Create a Security Group: ???
5. Create a EC2 instance: ???
```shell
aws ec2 run-instances \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=kata-i-user-data}]' \
    --instance-type t2.micro \
    --security-group-ids sg-xxxxxxxx \
    --subnet-id $SUBNET \
    --no-key-pair
```

    1. Name: `kata-i-user-data`
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
    1. Copy "Public IPv4 address" from `kata-i-user-data`
    2. Test: `curl -i http://50.19.33.2`

## Cleanup
1. Terminate instance: `kata-i-user-data`
2. Delete the Internet Gateway: `aws ec2 delete-internet-gateway --internet-gateway-id $IGW`
2. Delete VPC: `aws ec2 delete-vpc --vpc-id $VPC`
