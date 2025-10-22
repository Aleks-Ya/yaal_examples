# 070-stage-variables-lambda

## Task
Create a REST API with two Stages pointing to different aliases of a Lambd Function.

## Setup
1. Create a Function
	1. Create an Execution Role
		1. Trusted entity type: `AWS service`
		2. Use case: `Lambda`
		3. Permissions policies: `AWSLambdaBasicExecutionRole`
		4. Role name: `kata-role-stage-variables-lambda`
	2. Create a Function
		1. Author from scratch
		2. Function name: `kata-f-stage-variables-lambda`
		3. Runtime: `Python`
		4. Use an existing role: `kata-role-stage-variables-lambda`
	3. Create versions
		1. Create `DEV` version
			1. Update code:
				```python
				def lambda_handler(event, context):
					return "Hello, DEV!"
				```
			2. Deploy
			3. Test
			4. Publish new version
			5. Create alias
				1. Name: `DEV`
				2. Version: `1`
		2. Create `PRD` version
			1. Update code:
				```python
				def lambda_handler(event, context):
					return "Hello, PRD!"
				```
			2. Deploy
			3. Test
			4. Publish new version
			5. Create alias
				1. Name: `PRD`
				2. Version: `2`
			6. Add permissions
			```shell
			aws lambda add-permission \
				--function-name "arn:aws:lambda:us-east-1:523633434047:function:kata-f-stage-variables-lambda:PRD" \
				--source-arn "arn:aws:execute-api:us-east-1:523633434047:9own4k61zf/*/GET/" \
				--principal apigateway.amazonaws.com \
				--statement-id 24011bae-6bf4-401c-a89e-7841d072c2e2 \
				--action lambda:InvokeFunction
			```
2. Create a REST API
    1. Build a REST API
        1. Type: `New API`
        2. Name: `kata-api-stage-variables-lambda`
        3. API endpoint type: `Regional`
    2. Create a method
        1. Method type: `GET`
        2. Integration type: `Lambda function`
        3. Lambda function: `kata-f-stage-variables-lambda:${stageVariables.alias}`
	3. Deploy API
        1. Stage: `New stage`
        2. Stage name: `stage1`
    	3. Add a Stage Variable: 
        	1. Name: `alias`
        	2. Value: `DEV`
		4. Open the Invoke URL of `stage1` in browser
	4. Create the 2nd Stage
		1. Create Stage
			1. Stage name: `stage2`
			2. Deployment: latest
    		3. Add a Stage Variable: 
        		1. Name: `alias`
        		2. Value: `PRD`
    	3. Open the Invoke URL of `stage2` in browser

## Cleanup
1. Delete API: `kata-api-stage-variables-lambda`
2. Delete Function: `kata-f-stage-variables-lambda`
3. Delete Log Group: `/aws/lambda/kata-f-stage-variables-lambda`
4. Delete Role: `kata-role-stage-variables-lambda`

## History
- 2025-10-24 success
