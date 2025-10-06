# AWS SSM (Systems Manager) CLI

## Info
List EC2 instances with SSM Agent: `aws ssm describe-instance-information`

## Parameter Store
List all parameters: `aws ssm describe-parameters`
Show single parameter: `aws ssm get-parameter --name param1 --with-decryption`
Show single parameter value: `aws ssm get-parameter --name param1 --with-decryption --query "Parameter.Value" --output text`

## Session Manager
Start a session: `aws ssm start-session --target i-0020d4e5c90b3e23c`
