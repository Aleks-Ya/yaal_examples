# 120-ec2-instance-profile

## Task
Status: ?
Use CloudFormation to create an EC2 instance having an Instance Profile with the Infrastructure Composer.

## Steps
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create a Role
			1. Drag `Resources`-`AWS::IAM::Role`
			2. Logical ID: `Role`
			3. Resource configuration:
			```yaml
			RoleName: kata-role-ec2-instance-profile
			AssumeRolePolicyDocument:
			  Version: '2012-10-17'
			  Statement:
			    - Effect: Allow
			      Principal:
			        Service:
			          - ec2.amazonaws.com
			      Action:
			        - sts:AssumeRole
			ManagedPolicyArns:
			  - arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
			```
		2. Create an Instance Profile
			1. Drag `Resources`-`AWS::IAM::InstanceProfile`
			2. Logical ID: `InstanceProfile`
			3. Resource configuration:
			```yaml
			InstanceProfileName: kata-instance-profile-ec2-instance-profile
			Roles: 
			  - !Ref Role
			```
		3. Create an EC2 Instance
			1. Drag `Resources`-`AWS::EC2::Instance`
			2. Resource configuration:
			```yaml
			ImageId: ami-08982f1c5bf93d976
			InstanceType: t2.micro
			IamInstanceProfile: !Ref InstanceProfile
			Tags:
			  - Key: Name
			    Value: kata-i-ec2-instance-profile
			```
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-ec2-instance-profile`
3. Stack name: `kata-stack-ec2-instance-profile`
4. Click Submit
5. Test permissions
	1. Connect by Instance Connect
	2. Execute: `aws s3 ls`

## Cleanup
1. Delete Stack `kata-stack-ec2-instance-profile`
2. Delete Transfer Bucket `kata-bucket-ec2-instance-profile`
