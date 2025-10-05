# 010-user-data

## Task
Status: finished
Management interface: AWS CLI
Create a EC2 instance with a web-server which should start automatically.

## Setup
1. Create a VPC
    1. Create:
    ```shell 
    aws ec2 create-vpc \
        --cidr-block 10.0.0.0/28 \
        --tag-specifications 'ResourceType=vpc,Tags=[{Key=Name,Value="kata-vpc-user-data"}]'
    ```
    2. Get ID: 
    ```shell
    export VPC=$(aws ec2 describe-vpcs --query "Vpcs[*].VpcId" --output text \
        --filters "Name=tag:Name,Values=kata-vpc-user-data")
    ```
2. Creaet a Subnet
    1. Create:
    ```shell
    aws ec2 create-subnet --cidr-block 10.0.0.0/28 --vpc-id $VPC \
        --tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=kata-subnet-user-data}]'
    ```
    2. Get ID:
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
    1. Get Route Table ID:
    ```shell
    export RTB=$(aws ec2 describe-route-tables --query "RouteTables[*].RouteTableId" --output text \
        --filters "Name=vpc-id,Values=$VPC")
    ```
    2. Add route to Internet Gateway:
    ```shell
    aws ec2 create-route --destination-cidr-block 0.0.0.0/0 --route-table-id $RTB --gateway-id $IGW
    ```
5. Create a Security Group:
    1. Create: `aws ec2 create-security-group --vpc-id $VPC --group-name kata-sg-user-data --description "kata"`
    2. Get ID:
    ```shell
    export SG=$(aws ec2 describe-security-groups --query "SecurityGroups[*].GroupId" --output text \
      --filters Name=group-name,Values=kata-sg-user-data)
    ```
    2. Allow SSH: `aws ec2 authorize-security-group-ingress --group-id $SG --protocol tcp --port 22 --cidr 0.0.0.0/0`
    3. Allow HTTP: `aws ec2 authorize-security-group-ingress --group-id $SG --protocol tcp --port 80 --cidr 0.0.0.0/0`
6. Create a EC2 instance:
    1. Create
    ```shell
    aws ec2 run-instances \
        --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=kata-i-user-data}]' \
        --image-id ami-052064a798f08f0d3 \
        --instance-type t2.micro \
        --security-group-ids $SG \
        --subnet-id $SUBNET \
        --associate-public-ip-address \
        --user-data file://user-data.sh
    ```
    2. Get ID (ignore terminated):
    ```shell
    export INSTANCE=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId" --output text \
        --filters "Name=tag:Name,Values=kata-i-user-data" "Name=instance-state-name,Values=pending,running,stopping,stopped")
    ```
    3. Wait for running status: `aws ec2 wait instance-running --instance-ids $INSTANCE`
    4. Get public IP:
    ```shell
    export IP=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress" --output text \
        --instance-ids $INSTANCE)
    ```
2. Test web-server: `curl -i http://$IP`

## Cleanup
1. Terminate instance:
    1. Terminate: `aws ec2 terminate-instances --instance-ids $INSTANCE`
    2. Wait: `aws ec2 wait instance-terminated --instance-ids $INSTANCE`
2. Delete Security Group: `aws ec2 delete-security-group --group-id $SG`
3. Delete the Internet Gateway: 
    1. Detach from VPC: `aws ec2 detach-internet-gateway --internet-gateway-id $IGW --vpc-id $VPC`
    2. Delete: `aws ec2 delete-internet-gateway --internet-gateway-id $IGW`
4. Delete Subnet: `aws ec2 delete-subnet --subnet-id $SUBNET`
5. Delete VPC: `aws ec2 delete-vpc --vpc-id $VPC`
