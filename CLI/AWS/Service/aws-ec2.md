# AWS EC2 CLI

## Instances
List all EC2 instances: `aws ec2 describe-instances`
Create an EC2 instance: `aws ec2 run-instances ...`

## Regions
List all regions: `aws ec2 describe-regions --all-regions`
List all region names: `aws ec2 describe-regions --all-regions --query "Regions[].{Name:RegionName}"`

## VPCs
List VPCs: `aws ec2 describe-vpcs`
Create a VPC (16 IP addresses): `aws ec2 create-vpc --cidr-block 10.0.0.0/28`
