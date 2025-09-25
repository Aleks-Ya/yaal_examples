# 110-subnet-by-composer

## Task
Use CloudFormation to craete a Subnet with the Infrastructure Composer.

## Setup
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create a VPC
			1. Drag `Resources`-`AWS::EC2::VPC`
			2. Logical ID: `VPC`
			3. Resource configuration:
			```yaml
			CidrBlock: 10.0.0.0/28
			Tags: 
			  - Key: Name
			    Value: kata-vpc-subnet-by-composer
			```
		2. Create a Subnet
			1. Drag `Resources`-`AWS::EC2::Subnet`
			2. Resource configuration:
			```yaml
			VpcId: !Ref VPC
			CidrBlock: 10.0.0.0/28
			Tags: 
			  - Key: Name
			    Value: kata-subnet-subnet-by-composer
			```
		3. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-subnet-by-composer`
3. Stack name: `kata-stack-subnet-by-composer`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-subnet-by-composer`
2. Delete Transfer Bucket `kata-bucket-subnet-by-composer`
