# AWS EC2 CLI

## EC2

### Instance
List all EC2 instances: `aws ec2 describe-instances`
Stop instances: `aws ec2 stop-instances --instance-ids i-0298162c8d1220c46`
Start instances: `aws ec2 start-instances --instance-ids i-0298162c8d1220c46`
Show instance details: `aws ec2 describe-instances --instance-ids i-0298162c8d1220c46`
Wait until the instance got `running` status: `aws ec2 wait instance-running --instance-ids i-083a752a1597a0111`
Wait until the instance has stopped: `aws ec2 wait instance-stopped --instance-ids i-0799e1b8bfe20f9fb`
Wait until the instance has started: `aws ec2 wait instance-status-ok --instance-ids i-0799e1b8bfe20f9fb`
Create an instance:
```shell
aws ec2 run-instances \
  --image-id ami-xxxxxxxx \
  --instance-type t2.micro \
  --key-name MyKeyPair \
  --security-group-ids sg-xxxxxxxx \
  --subnet-id subnet-xxxxxxxx
```

### Region
List all regions: `aws ec2 describe-regions --all-regions`
List all region names: `aws ec2 describe-regions --all-regions --query "Regions[].{Name:RegionName}"`

### Volume
List volumes: `aws ec2 describe-volumes`
Detach a volume from an instance: `aws ec2 detach-volume --volume-id vol-09cb491c3b9c82558`
Show volume details: `aws ec2 describe-volumes --volume-ids vol-09cb491c3b9c82558`
Attach a volume to an instance: `aws ec2 attach-volume --device /dev/xvdb --instance-id i-05931912fe1bff8f2 --volume-id vol-09cb491c3b9c82558`


## VPC

### VPC
List VPCs: `aws ec2 describe-vpcs`
Find VPC ID by name: 
```shell
export VPC=$(aws ec2 describe-vpcs --query "Vpcs[*].VpcId" --output text --filters "Name=tag:Name,Values=vpc1")
```
Create a VPC (16 IP addresses): `aws ec2 create-vpc --cidr-block 10.0.0.0/28`
Create a VPC with given name: 
```shell 
aws ec2 create-vpc \
	--cidr-block 10.0.0.0/28 \
	--tag-specifications 'ResourceType=vpc,Tags=[{Key=Name,Value="vpc1"}]'
```
Delete a VPC: `aws ec2 delete-vpc --vpc-id vpc-0bb219aad05d59494`

### Subnet
Create a subnet in VPC: `aws ec2 create-subnet --cidr-block 10.0.0.0/28 --vpc-id vpc-0bb219aad05d59494`
Create a subnet with given name:
```shell
aws ec2 create-subnet \
	--cidr-block 10.0.0.0/28 \
	--vpc-id vpc-0bb219aad05d59494 \
	--tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=subnet1}]'
```
Get Subnet ID by name:
```shell
export SUBNET=$(aws ec2 describe-subnets --query "Subnets[*].SubnetId" --output text \
	  --filters "Name=tag:Name,Values=<subnet-name>")
```

### Network Interface
List ENIs: `aws ec2 describe-network-interfaces`

### Elastic IP
List EIPs: `aws ec2 describe-addresses`

### Internet Gateway
Create an Internet Gateway with given name:
```shell
aws ec2 create-internet-gateway \
	--tag-specifications 'ResourceType=internet-gateway,Tags=[{Key=Name,Value=}]'
```
Get Internet Gateway ID by name:
```shell
export IGW=$(aws ec2 describe-internet-gateways --query "InternetGateways[0].InternetGatewayId" --output text \
  --filters "Name=tag:Name,Values=<name>")
```
Delete an Internet Gateway: `aws ec2 delete-internet-gateway --internet-gateway-id igw-0465bbc89f3f6d966`
Attach an Internet Gateway to a VPC: `aws ec2 attach-internet-gateway --internet-gateway-id igw-0465bbc89f3f6d966 --vpc-id vpc-0bb219aad05d59494`

### Route table
List all Route Tables: `aws ec2 describe-route-tables`
List Route Tables in a VPC: `aws ec2 describe-route-tables --filters "Name=vpc-id,Values=vpc-0bb219aad05d59494"`
Add route to Internet Gateway:
```shell
aws ec2 create-route --destination-cidr-block 0.0.0.0/0 \
	--route-table-id rtb-0af6ee9e5ac66b6ce \
	--gateway-id igw-0465bbc89f3f6d966
```
Get Route Table ID in a VPC:
```shell
export RTB=$(aws ec2 describe-route-tables --query "RouteTables[*].RouteTableId" --output text \
	--filters "Name=vpc-id,Values=vpc-0bb219aad05d59494")
```
