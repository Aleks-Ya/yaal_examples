# 040-iam-user

## Task
Status: ?
Use CloudFormation to craete an IAM User with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Drag `Resources`-`AWS::IAM::User`
		2. Resource configuration:
		```yaml
		UserName: kata-iam-user-user
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
			2. Transfer bucket name: `kata-bucket-iam-user`
3. Stack name: `kata-stack-iam-user`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-iam-user`
2. Delete Transfer Bucket `kata-bucket-iam-user`
