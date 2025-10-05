# 100-route-table

## Task
Status: ?
Use CloudFormation to craete a Route Table with the Infrastructure Composer.

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
			    Value: kata-vpc-route-table
			```
		2. Create a Route Table
			1. Drag `Resources`-`AWS::EC2::RouteTable`
			2. Resource configuration:
			```yaml
			VpcId: !Ref VPC
			Tags: 
			  - Key: Name
			    Value: kata-rt-route-table
			```
		3. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-route-table`
3. Stack name: `kata-stack-route-table`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-route-table`
2. Delete Transfer Bucket `kata-bucket-route-table`
