# 030-iam-role

## Task
Status: ?
Use CloudFormation to craete an IAM Role with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Drag `Resources`-`AWS::IAM::Role`
		2. Resource configuration:
		```yaml
		RoleName: kata-role-iam-role
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
		  - arn:aws:iam::aws:policy/AmazonEC2ReadOnlyAccess
		Policies:
		  - PolicyName: MyInlinePolicy
		    PolicyDocument:
		      Version: "2012-10-17"
		      Statement:
		        - Effect: Allow
		          Action:
		            - s3:ListBucket
		          Resource: arn:aws:s3:::example-bucket
		```
		3. Create
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-iam-role`
3. Stack name: `kata-stack-iam-role`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-iam-role`
2. Delete Transfer Bucket `kata-bucket-iam-role`
