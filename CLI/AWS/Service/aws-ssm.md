# AWS SSM (Systems Manager) CLI

## Info
List EC2 instances with SSM Agent: `aws ssm describe-instance-information`

## Parameter Store
List all parameters: `aws ssm describe-parameters`
Show single parameter: `aws ssm get-parameter --name param1 --with-decryption`
Show single parameter value: `aws ssm get-parameter --name param1 --with-decryption --query "Parameter.Value" --output text`
Create a String parameter: `aws ssm put-parameter --type String --name /my/param1 --value "text value"`
Create a StringList parameter: `aws ssm put-parameter --type StringList --name /my/param1 --value "value1,value2"`

## Session Manager
Start a session: `aws ssm start-session --target i-0020d4e5c90b3e23c`

## SSM Agent (in EC2 shell)
Show status: `systemctl status amazon-ssm-agent`
Restart service: `sudo systemctl restart amazon-ssm-agent`
Tail logs: `sudo tail -f /var/log/amazon/ssm/amazon-ssm-agent.log`
Install TelNet: `sudo dnf install -y telnet`
Test connection to SSM endpoints:
- `telnet ssm.us-east-1.amazonaws.com 443`
- `telnet ssmmessages.us-east-1.amazonaws.com 443`
