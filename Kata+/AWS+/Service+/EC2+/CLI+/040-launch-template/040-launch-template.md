# 040-launch-template

## Task
Run an EC2 Instance from a Launch Template.

## Steps
1. Open a new terminal
2. Change current dir
3. Set env vars
    ```shell
    set -x
    export LT_NAME=kata-lt-launch-template
    ```
4. Create a Launch Template:
	1. Create: 
		`aws ec2 create-launch-template --launch-template-name $LT_NAME --launch-template-data file://lt-data.json`
	2. Get ID:
		```shell
		export LT_ID=$(aws ec2 describe-launch-templates --query 'LaunchTemplates[0].LaunchTemplateId' \
		  --output text --launch-template-names $LT_NAME)
		```
5. Run an EC2 Instance:
	1. Run: `aws ec2 run-instances --launch-template LaunchTemplateId=$LT_ID,Version='$Default'`
	2. Get Instance IDs: 
		```shell
		export I_IDS=$(aws ec2 describe-instances --output text \
  		  --filters "Name=tag:aws:ec2launchtemplate:id,Values=$LT_ID" \
  		  --query "Reservations[].Instances[].InstanceId")
		```

## Cleanup
1. Terminate instance:
    1. Terminate: `aws ec2 terminate-instances --instance-ids $I_IDS`
    2. Wait: `aws ec2 wait instance-terminated --instance-ids $I_IDS`
2. Delete the Launch Template: `aws ec2 delete-launch-template --launch-template-name $LT_NAME`
3. Close the terminal

## History
- 2026-02-12 success
