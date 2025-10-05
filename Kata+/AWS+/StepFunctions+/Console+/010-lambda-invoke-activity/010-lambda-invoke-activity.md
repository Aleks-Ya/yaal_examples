# 010-lambda-invoke-activity

## Task
Create a StateMachine that triggers a Lambda Function.

## Setup
1. Create Function
	1. Template: `Author from scratch`
	2. Function name: `kata-lambda-invoke-activity-function`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
2. Create StateMachine
	1. Name: `kata-lambda-invoke-activity-sm`
	2. State machine type: `Standard`
3. Configure StateMachine
	1. Create `Lambda Invoke` state
		1. Add an `Lambda Invoke` action
		2. Execute "Test state"
4. Execute StateMachine

## Cleanup
1. Delete Function
	1. Delete Function `kata-lambda-invoke-activity-function`
	2. Delete Policy `kata-lambda-invoke-activity-function-role-0emvw64d`
2. Delete StateMachine
	1. Delete StateMachine `kata-lambda-invoke-activity-sm`
	2. Delete Execution Role `	StepFunctions-kata-lambda-invoke-activity-sm-role-49qdc5oq0`
