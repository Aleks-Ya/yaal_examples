# 020-ec2-instance

## Task
Status: ?
Use CloudFormation to create an EC2 Instance with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create an EC2 Instance
			1. Drag `Resources`-`AWS::EC2::Instance`
			2. Resource configuration:
			```yaml
			ImageId: ami-08982f1c5bf93d976
			InstanceType: t2.micro
			Tags:
			  - Key: Name
			    Value: kata-i-ec2-instance
			```
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-ec2-instance`
3. Stack name: `kata-stack-ec2-instance`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-ec2-instance`
2. Delete Transfer Bucket `kata-bucket-ec2-instance`
