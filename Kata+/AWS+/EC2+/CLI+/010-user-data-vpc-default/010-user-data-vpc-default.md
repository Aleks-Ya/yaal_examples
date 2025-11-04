# 010-user-data-vpc-default

## Task
Management interface: AWS CLI
Create an EC2 instance (in the default VPC) with a web-server which should start automatically.

## Steps
1. Change current dir
2. Set environment variables
   ```shell
   set -x
   export I_NAME=kata-i-user-data-vpc-default
   ```
3. Create a EC2 instance:
    1. Create
    ```shell
    aws ec2 run-instances \
        --tag-specifications "ResourceType=instance,Tags=[{Key=Name,Value=$I_NAME}]" \
        --image-id ami-052064a798f08f0d3 \
        --instance-type t2.micro \
        --associate-public-ip-address \
        --user-data file://user-data.sh
    ```
    2. Get ID (ignore terminated):
    ```shell
    export I_ID=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId" --output text \
        --filters "Name=tag:Name,Values=$I_NAME" "Name=instance-state-name,Values=pending,running,stopping,stopped")
    ```
    3. Wait for running status: `aws ec2 wait instance-status-ok --instance-ids $I_ID`
    4. Get public IP:
    ```shell
    export I_IP=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress" --output text \
        --instance-ids $I_ID)
    ```
4. Test web-server: `curl -i http://$I_IP`

## Cleanup
1. Terminate instance:
    1. Terminate: `aws ec2 terminate-instances --instance-ids $I_ID`
    2. Wait: `aws ec2 wait instance-terminated --instance-ids $I_ID`
2. Unset env vars: `set +x; unset I_NAME I_ID I_IP`

## History
- 2025-11-04 success
