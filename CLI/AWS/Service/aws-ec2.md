# AWS EC2 CLI

## EC2

### Instance
List all EC2 instances: `aws ec2 describe-instances`
Show instance details: `aws ec2 describe-instances --instance-ids i-0298162c8d1220c46`
Start instances: `aws ec2 start-instances --instance-ids i-0298162c8d1220c46`
Stop instances: `aws ec2 stop-instances --instance-ids i-0298162c8d1220c46`
Terminate instance: `aws ec2 terminate-instances --instance-ids i-0298162c8d1220c46`
Wait for instance status:
- running: `aws ec2 wait instance-running --instance-ids i-083a752a1597a0111`
- stopped: `aws ec2 wait instance-stopped --instance-ids i-0799e1b8bfe20f9fb`
- started: `aws ec2 wait instance-status-ok --instance-ids i-0799e1b8bfe20f9fb`
- terminated: `aws ec2 wait instance-terminated --instance-ids i-0799e1b8bfe20f9fb`
Create an instance:
```shell
aws ec2 run-instances \
  --image-id ami-xxxxxxxx \
  --instance-type t2.micro \
  --key-name MyKeyPair \
  --security-group-ids sg-xxxxxxxx \
  --subnet-id subnet-xxxxxxxx
```
Get Instance ID by name:
```shell
export INSTANCE=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId" --output text \
  --filters "Name=tag:Name,Values=MyInstanceName")
```
Get public IP of an Instance:
```shell
export IP=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress" --output text \
	--instance-ids i-0799e1b8bfe20f9fb)
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
Show subnet by ID: `aws ec2 describe-subnets --subnet-ids subnet-02875e4552340f24c`
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
Delete Subnet: `aws ec2 delete-subnet --subnet-id subnet-02875e4552340f24c`

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
Detach from a VPC: `aws ec2 detach-internet-gateway --internet-gateway-id igw-0465bbc89f3f6d966 --vpc-id vpc-0bb219aad05d59494`

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

### Security Group
Create a Security Group: `aws ec2 create-security-group --group-name group1 --description "description1"`
Allow inboud traffict (in any VPC):
- SSH anywhere: `aws ec2 authorize-security-group-ingress --group-id sg-0f4cedcb0796f3b83 --protocol tcp --cidr 0.0.0.0/0 --port 22`
- HTTP from anywhere: `aws ec2 authorize-security-group-ingress --group-id sg-0f4cedcb0796f3b83 --protocol tcp --cidr 0.0.0.0/0 --port 80`
Get Security Goup ID by name:
```shell
export SG=$(aws ec2 describe-security-groups --query "SecurityGroups[*].GroupId" --output text \
  --filters Name=group-name,Values=group1)
```
Delete Security Group by name (in default VPC): `aws ec2 delete-security-group --group-name group1`
Delete Security Group by ID (in any VPC): `aws ec2 delete-security-id --group-id sg-0f4cedcb0796f3b83`
