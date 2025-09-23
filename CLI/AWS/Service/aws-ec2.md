# AWS EC2 CLI

## Instances
List all EC2 instances: `aws ec2 describe-instances`
Create an EC2 instance: `aws ec2 run-instances ...`
Stop instances: `aws ec2 stop-instances --instance-ids i-0298162c8d1220c46`
Start instances: `aws ec2 start-instances --instance-ids i-0298162c8d1220c46`
Show instance details: `aws ec2 describe-instances --instance-ids i-0298162c8d1220c46`
Wait until the instance got `running` status: `aws ec2 wait instance-running --instance-ids i-083a752a1597a0111`
Wait until the instance has stopped: `aws ec2 wait instance-stopped --instance-ids i-0799e1b8bfe20f9fb`
Wait until the instance has started: `aws ec2 wait instance-status-ok --instance-ids i-0799e1b8bfe20f9fb`

## Regions
List all regions: `aws ec2 describe-regions --all-regions`
List all region names: `aws ec2 describe-regions --all-regions --query "Regions[].{Name:RegionName}"`

## VPCs
List VPCs: `aws ec2 describe-vpcs`
Create a VPC (16 IP addresses): `aws ec2 create-vpc --cidr-block 10.0.0.0/28`

## Volumes
List volumes: `aws ec2 describe-volumes`
Detach a volume from an instance: `aws ec2 detach-volume --volume-id vol-09cb491c3b9c82558`
Show volume details: `aws ec2 describe-volumes --volume-ids vol-09cb491c3b9c82558`
Attach a volume to an instance: `aws ec2 attach-volume --device /dev/xvdb --instance-id i-05931912fe1bff8f2 --volume-id vol-09cb491c3b9c82558`

## Network Interfaces
List ENIs: `aws ec2 describe-network-interfaces`
