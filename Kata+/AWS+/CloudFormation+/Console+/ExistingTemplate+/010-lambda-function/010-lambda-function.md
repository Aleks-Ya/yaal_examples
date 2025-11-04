# 010-lambda-function

## Task
Status: ?
Use CloudFormation to create a Lambda function by importing a Template in the Management Console.

## Steps
1. Change the current dir
2. Create a Deployment Package: `zip deployment-package.zip handler.py`
2. Create an Execution Role: 
	1. Trusted entity type: `AWS service`
	2. Service or use case: `Lambda`
	3. Permissions: -
	4. Role name: `kata-role-lambda-function-by-console-import`
1. Create stack, with new resources (standard)
2. Prepare template: `Create template in Designer`
	1. Add a Lambda Execution Role
		1. Resource types -> IAM -> Role
		2. Copy properties from `lambda-template.json`
	2. Add a Lambda Function
		1. Resource types -> Role
		2. Copy properties from `lambda-template.json`
	3. Validate the template
	4. Click `Create stack`
3. Stack name: `kata-stack-create-lambda`
4. Click Submit
5. Execute the Function

## Cleanup
1. Delete the stack
