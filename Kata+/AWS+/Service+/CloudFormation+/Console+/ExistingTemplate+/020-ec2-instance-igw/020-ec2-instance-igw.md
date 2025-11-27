# 020-ec2-instance-igw

## Task
Use CloudFormation to create an EC2 Instance with the Infrastructure Composer.
The EC2 Instance is running a web-server available from the Internet.

## Steps
1. Create stack
	1. Prepare template: `Choose an existing template`
	2. Template source: `Upload a template file`
	3. Upload a template file: `template.yaml`
2. Stack name: `kata-stack-ec2-instance-igw`
3. Click Submit
4. Test via EC2 Instance public IP: `curl 44.192.53.23`

## Cleanup
1. Delete Stack `kata-stack-ec2-instance-igw`
2. Delete Transfer Bucket `cf-templates-*`

## History
- 2025-11-08 success
