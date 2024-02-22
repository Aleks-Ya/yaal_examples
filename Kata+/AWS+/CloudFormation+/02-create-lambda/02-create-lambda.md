# 02-create-lambda

## Task
Create a Lambda function using CloudFormation.

## Setup
By AWS Console.
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
3. Stack name: `stack-1`
4. Click Submit
5. Execute the Function

## Cleanup
1. Delete the stack
