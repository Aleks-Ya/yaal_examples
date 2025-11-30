# 010-connect-ssm-agent-default-vpc

## Task
Connect to an SSM Agent withing an EC2 instance in the default VPC.

## Steps
1. Change current dir
2. Set env vars
	```shell
	export ROLE=kata-role-connect-ssm-agent-default-vpc
	export I_PROFILE=kata-profile-connect-ssm-agent-default-vpc
	export I_NAME=kata-i-connect-ssm-agent-default-vpc
	```
3. Create an Instance Profile
	1. Create Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Attach Policy: `aws iam attach-role-policy --role-name $ROLE --policy-arn arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore`
4. Create an Instance Profile
	1. Create Instance Profile: `aws iam create-instance-profile --instance-profile-name $I_PROFILE`
	2. Attach a role to instance profile: `aws iam add-role-to-instance-profile --instance-profile-name $I_PROFILE --role-name $ROLE`
5. Create an EC2 instance
    1. Create
    ```shell
    aws ec2 run-instances \
        --tag-specifications "ResourceType=instance,Tags=[{Key=Name,Value=$I_NAME}]" \
        --image-id ami-052064a798f08f0d3 \
        --instance-type t2.micro \
        --iam-instance-profile Name=$I_PROFILE
    ```
	2. Get Instance ID by name:
	```shell
	export I_ID=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId" --output text \
  		--filters "Name=tag:Name,Values=$I_NAME")
	```
	3. Get public IP of an Instance:
	```shell
	export I_IP=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress" --output text --instance-ids $I_ID)
	```
6. Find instance: `aws ssm describe-instance-information`

## Cleanup
1. Terminate Instance `aws ec2 terminate-instances --instance-ids $I_ID`
2. Delete Instance Profile: 
	1. Detach Role: `aws iam remove-role-from-instance-profile --instance-profile-name $I_PROFILE --role-name $ROLE`
	2. Delete Instance Profile: `aws iam delete-instance-profile --instance-profile-name $I_PROFILE`
3. Delete Role
	1. Detach Policy: `aws iam detach-role-policy --role-name $ROLE --policy-arn arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore`
	2. Delete Role `aws iam delete-role --role-name $ROLE`
4. Delete env vars: `unset ROLE I_PROFILE I_NAME I_ID I_IP`

## History
- 2025-10-12 success
