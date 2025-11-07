# 010-lambda-function

## Task
Use CloudFormation to create a Lambda function by importing a Template in the Management Console.

## Steps
1. Create stack
	1. Prepare template: `Choose an existing template`
	2. Template source: `Upload a template file`
	3. Upload a template file: `template.yaml`
2. Stack name: `kata-stack-lambda-function`
3. Click Submit
4. Execute the Function

## Cleanup
1. Delete stack `kata-stack-lambda-function`
2. Delete Transfer Bucket `cf-templates-*`

## History
- 2025-11-08 success
