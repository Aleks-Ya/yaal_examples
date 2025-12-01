# 010-lambda-invoke-action

## Task
Create a StateMachine that triggers a Lambda Function.

## Steps
1. Create Function
	1. Create an execution role:
		1. Trusted entity type: `AWS service`
		2. Use case: `Lambda`
		3. Permissions policies: `AWSLambdaBasicExecutionRole`
		4. Role name: `kata-role-lambda-invoke-action-function`
	2. Create Function:
		1. Template: `Author from scratch`
		2. Function name: `kata-f-lambda-invoke-action`
		3. Runtime: `Python`
		4. Architecture: `x86_64`
		5. Permissions:
			1. Execution role: Use an existing role `kata-role-lambda-invoke-action-function`
2. Create StateMachine
	1. Name: `kata-sm-lambda-invoke-action`
	2. State machine type: `Standard`
3. Configure StateMachine
	1. Create `Lambda Invoke` state
		1. Add an `Lambda Invoke` action
		2. Function name: `kata-f-lambda-invoke-action:$LATEST`
	2. Create StateMachine
	3. Confirm role creation
	4. Test the `Lambda Invoke` action:
		1. Click the action
		2. Click "Test state"
4. Execute StateMachine

## Cleanup
1. Delete StateMachine
	1. Delete StateMachine `kata-sm-lambda-invoke-action`
	2. Delete Execution Role `StepFunctions-kata-sm-lambda-invoke-action-role-49qdc5oq0`
2. Delete Function
	1. Delete Function `kata-f-lambda-invoke-action`
	2. Delete Role `kata-role-lambda-invoke-action-function`

## History
- 2025-12-01 success
