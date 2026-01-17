# 025-ec2-instance-metrics

## Task
See metrics from EC2 instance in CloudWatch.

## Steps
1. Launch EC2 instance
    1. Name: `kata-i-ec2-instance-metrics`
    2. OS: `Amazon Linux`
    3. Instance type: `t3.micro`
    4. Key pair name: `Proceed without a key pair`
    5. Network settings: default
    6. Firewall (security groups):
        1. Select existing security group: `default`
    7. IAM instance profile
        1. Create new
            1. Trusted entity type: `AWS service`
            2. Service or use case: `EC2`
            3. Use case: `EC2`
            4. Permissions policies: `CloudWatchAgentServerPolicy`
            5. Role name: `kata-role-ec2-instance-metrics`
        2. Choose `kata-role-ec2-instance-metrics`
2. Install CloudWatch Logs Agent
    1. Connect via Instance Connect
    2. Install package: `sudo dnf install -y amazon-cloudwatch-agent`
    3. Configure the Agent: `sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard`
        1. On which OS are you planning to use the agent? `linux`
        2. Are you using EC2 or On-Premises hosts? `EC2`
        3. Which user are you planning to run the agent? `cwagent`
        4. Do you want to turn on StatsD daemon? `no`
        5. Do you want to monitor metrics from CollectD? `no`
        6. Do you want to monitor any host metrics? `yes`
            1. Do you want to monitor cpu metrics per core? `yes`
            2. Do you want to add ec2 dimensions into all of your metrics if the info is available? `yes`
            3. Do you want to aggregate ec2 dimensions (InstanceId)? `yes`
            4. Would you like to collect your metrics at high resolution (sub-minute resolution)? `10s`
            5. Which default metrics config do you want? `Basic`
            6. Are you satisfied with the above config? `yes`
        7. Do you have any existing CloudWatch Log Agent configuration file to import for migration? `no`
        8. Do you want to monitor any log files? `no`
        9. Do you want to specify any additional log files to monitor? `no`
        10. Do you want the CloudWatch agent to also retrieve X-ray traces? `no`
        11. Do you want to store the config in the SSM parameter store? `no`
    4. Start the Agent:
       ```shell
       sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s \
       	-c file:///opt/aws/amazon-cloudwatch-agent/bin/config.json
       ```
    5. Check Agent status: `/opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a status`
3. See memory in CloudWatch
    1. Open CloudWatch -> Metrics -> All metrics
    2. Choose `Browse` -> `Custom namespaces` -> `CWAgent`
    3. Select `mem_used_percent`

## Cleanup
1. Terminate EC2 instance `kata-i-ec2-instance-metrics`
2. Delete IAM role `kata-role-ec2-instance-metrics`
3. Delete Metrics: impossible

## History
- 2026-01-19 success
