# 02-ec2-instance-logs

## Task

See logs from EC2 instance in CloudWatch.

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
        6. Do you want to monitor any host metrics? `no`
        7. Do you have any existing CloudWatch Log Agent configuration file to import for migration? `no`
        8. Do you want to monitor any log files? `yes`
            1. Log file path: `/var/log/my.log`
            2. Log group name: `my-ec2`
            3. Log stream name: `{instance_id}`
            4. Log Group Retention in days: `-1`
        9. Do you want to specify any additional log files to monitor? `no`
        10. Do you want the CloudWatch agent to also retrieve X-ray traces? `no`
        11. Do you want to store the config in the SSM parameter store? `no`
    4. Start the Agent:
       ```bash
       sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s \
       -c file:///opt/aws/amazon-cloudwatch-agent/bin/config.json
       ```
    5. Check Agent status: `sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a status`
3. See log in CloudWatch
    1. Append log: `sudo sh -c "echo abc >> /var/log/my.log"`
    2. Open CloudWatch, log group: `my-ec2`

## Cleanup

1. Terminate EC2 instance `instance-1`
2. Delete IAM role `cloud-watch-agent-role`
