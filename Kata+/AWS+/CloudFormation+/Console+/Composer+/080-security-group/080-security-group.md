# 080-security-group

## Task
Status: ?
Use CloudFormation to craete a Security Group with the Infrastructure Composer.

## Steps
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
			    Value: kata-vpc-security-group
			```
		2. Create a Security Group
			1. Drag `Resources`-`AWS::EC2::SecurityGroup`
			2. Resource configuration:
			```yaml
			GroupName: kata-sg-security-group
			VpcId: !Ref VPC
			```
		3. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-security-group`
3. Stack name: `kata-stack-security-group`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-security-group`
2. Delete Transfer Bucket `kata-bucket-security-group`
