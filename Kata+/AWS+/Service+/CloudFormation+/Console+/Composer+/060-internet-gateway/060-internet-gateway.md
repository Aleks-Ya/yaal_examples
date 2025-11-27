# 060-internet-gateway

## Task
Use CloudFormation to craete an Internet Gateway with the Infrastructure Composer.

## Steps
1. "Create stack" -> "With new resources (standard)"
2. Prepare template: `Build from Infrastructure Composer`
	1. `Create a template in Infrastructure Composer`
		1. Create an Internet Gateway
			1. Drag `Resources`-`AWS::EC2::InternetGateway`
			2. Resource configuration: `{}`
		2. Create Stack
			1. Click `Create template`
			2. Transfer bucket name: `kata-bucket-internet-gateway`
3. Stack name: `kata-stack-internet-gateway`
4. Click Submit

## Cleanup
1. Delete Stack `kata-stack-internet-gateway`
2. Delete Transfer Bucket `kata-bucket-internet-gateway`

## History
