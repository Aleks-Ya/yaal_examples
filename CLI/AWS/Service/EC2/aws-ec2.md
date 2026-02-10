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
Attach a volume to an instance:
  `aws ec2 attach-volume --device /dev/xvdb --instance-id i-05931912fe1bff8f2 --volume-id vol-09cb491c3b9c82558`

## Launch Template
List Launch Templates: `aws ec2 describe-launch-templates`
Show details of a version of a Launch Template: 
  `aws ec2 describe-launch-template-versions --versions $Latest --launch-template-id lt-0123456789abcdef0`
Create a Launch Template: 
  `aws ec2 create-launch-template --launch-template-name lt-1 --launch-template-data file://lt-data.json`
Run EC2 Instances from a Launch Template:
  `aws ec2 run-instances --count 1 --launch-template LaunchTemplateId=lt-07da994823eccbec4,Version='$Default'`
Get Launch Template ID by its name:
```shell
export LT_ID=$(aws ec2 describe-launch-templates --query 'LaunchTemplates[0].LaunchTemplateId' \
  --output text --launch-template-names lt-1)
```
List EC2 Instances started from a Launch Template:
```shell
aws ec2 describe-instances --output text \
  --filters "Name=tag:aws:ec2launchtemplate:id,Values=lt-07da994823eccbec4" \
  --query "Reservations[].Instances[].InstanceId"
```
Delete a Launch Template:
- By name: `aws ec2 delete-launch-template --launch-template-name my-template`
- By ID: `aws ec2 delete-launch-template --launch-template-id lt-0123456789abcdef0`
