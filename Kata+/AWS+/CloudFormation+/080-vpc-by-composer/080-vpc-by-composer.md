# 080-vpc-by-composer

## Task
Use CloudFormation to craete a VPC with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create a VPC
			1. Drag `Resources`-`AWS::EC2::VPC`
			2. Resource configuration:
			```yaml
			CidrBlock: 10.0.0.0/28
			Tags: 
			  - Key: Name
			    Value: kata-vpc-vpc-by-composer
			```
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-vpc-by-composer`
3. Stack name: `kata-stack-vpc-by-composer`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-vpc-by-composer`
2. Delete Transfer Bucket `kata-bucket-vpc-by-composer`
