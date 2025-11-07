# 050-iam-group

## Task
Use CloudFormation to craete an IAM Group with the Infrastructure Composer.

## Steps
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create a Group
			1. Drag `Resources`-`AWS::IAM::Group`
			2. Resource configuration:
			```yaml
			GroupName: kata-iam-group-group
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
		2. Create user John
			1. Drag `Resources`-`AWS::IAM::User`
			2. Resource configuration:
			```yaml
			UserName: kata-iam-group-john-user
			Groups: 
			  - kata-iam-group-group
			```
		3. Create user Mary
			1. Drag `Resources`-`AWS::IAM::User`
			2. Resource configuration:
			```yaml
			UserName: kata-iam-group-mary-user
			Groups: 
			  - kata-iam-group-group
			```
		4. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-iam-group`
3. Stack name: `kata-stack-iam-group`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-iam-group`
2. Delete Transfer Bucket `kata-bucket-iam-group`

## History
