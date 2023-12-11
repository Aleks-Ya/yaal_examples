# 03-ec2-instance-alert

## Task

Create an CloudWatch alert that raises if ≥80% of EC2 instance RAM is busy.

## Setup

1. Launch EC2 instance
    1. Name: `instance-1`
    2. OS: `Amazon Linux`
    3. Instance type: `t2.micro`
    4. Key pair name: `Proceed without a key pair`
    5. Network settings: default
    6. IAM instance profile
        1. Create new
            1. Trusted entity type: `AWS service`
            2. Service or use case: `EC2`
            3. Use case: `EC2`
            4. Permissions policies: `CloudWatchAgentServerPolicy`
            5. Role name: `cloud-watch-agent-role`
        2. Choose `cloud-watch-agent-role`
2. Install CloudWatch Logs Agent
    1. Connect via Instance Connect
    2. Install package: `sudo yum install -y amazon-cloudwatch-agent`
    3. Configure the Agent: `sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard`
        1. On which OS are you planning to use the agent? `linux`
        2. Are you using EC2 or On-Premises hosts? `EC2`
        3. Which user are you planning to run the agent? `root`
        4. Do you want to turn on StatsD daemon? `no`
        5. Do you want to monitor metrics from CollectD? `no`
        6. Do you want to monitor any host metrics? `yes`
            1. Do you want to monitor cpu metrics per core? `no`
            2. Do you want to add ec2 dimensions into all of your metrics if the info is available? `yes`
            3. Do you want to aggregate ec2 dimensions (InstanceId)? `no`
            4. Would you like to collect your metrics at high resolution (sub-minute resolution)? `60s`
            5. Which default metrics config do you want? `Basic`
            6. Are you satisfied with the above config? `yes`
        7. Do you have any existing CloudWatch Log Agent configuration file to import for migration? `no`
        8. Do you want to monitor any log files? `no`
        9. Do you want the CloudWatch agent to also retrieve X-ray traces? `no`
        10. Do you want to store the config in the SSM parameter store? `no`
    4. Start the Agent:
       ```bash
       sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s \
       -c file:///opt/aws/amazon-cloudwatch-agent/bin/config.json
       ```
    5. Check Agent status: `sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a status`
3. See memory in CloudWatch
    1. Open CloudWatch -> Metrics -> All metrics
    2. Choose `Browse` tab
    3. Search by `mem_used_percent` or InstanceID
4. Create an Alarm
    1. Alarm state trigger: `In alarm`
    2. Send a notification to the following SNS topic: `Create new topic`
        1. Create a new topic...: `Default_CloudWatch_Alarms_Topic`
        2. Email endpoints that will receive the notification...: `ya_al@bk.ru`
        3. Confirm subscription
    3. Alarm name: `ec2-memory-alarm`
    4. Increase memory consumption
        1. Install: `sudo yum install stress-ng`
        2. Consume: `stress-ng --vm-bytes 500M -m 1 --vm-keep`

## Cleanup

1. Delete Alarm
2. Delete SNS Topic
3. Teminate EC2 Instance
