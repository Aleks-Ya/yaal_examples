# AWS EC2 CLI

## Instance
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

## Region
List all regions: `aws ec2 describe-regions --all-regions`
List all region names: `aws ec2 describe-regions --all-regions --query "Regions[].{Name:RegionName}"`

## Volume
List volumes: `aws ec2 describe-volumes`
Detach a volume from an instance: `aws ec2 detach-volume --volume-id vol-09cb491c3b9c82558`
Show volume details: `aws ec2 describe-volumes --volume-ids vol-09cb491c3b9c82558`
Attach a volume to an instance: `aws ec2 attach-volume --device /dev/xvdb --instance-id i-05931912fe1bff8f2 --volume-id vol-09cb491c3b9c82558`
