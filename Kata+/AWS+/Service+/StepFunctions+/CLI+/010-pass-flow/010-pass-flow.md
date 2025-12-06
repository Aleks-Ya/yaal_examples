# 010-pass-flow

## Task
Use a Pass flow state.

## Steps
1. Change current dir
2. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export SM_NAME=kata-sm-pass-flow
	export SM_ARN=arn:aws:states:us-east-1:$ACCOUNT:stateMachine:$SM_NAME
	export SM_ROLE_NAME=kata-role-pass-flow
	export SM_ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$SM_ROLE_NAME
	export POLICY_ARN=arn:aws:iam::aws:policy/AWSStepFunctionsFullAccess
	export EXECUTION_NAME=execution1
	export EXECUTION_ARN=arn:aws:states:us-east-1:$ACCOUNT:execution:$SM_NAME:$EXECUTION_NAME
	```
3. Create an execution role:
	1. Create a Role: 
		```shell
		aws iam create-role --role-name $SM_ROLE_NAME --assume-role-policy-document file://sm-trust-policy.json
		```
	2. Assign permissions: `aws iam attach-role-policy --role-name $SM_ROLE_NAME --policy-arn $POLICY_ARN`
4. Create a State Machine:
	```shell
	aws stepfunctions create-state-machine \
		--name $SM_NAME \
		--definition file://sm.json \
		--role-arn $SM_ROLE_ARN
	```
5. Execute
	1. Start execution: `aws stepfunctions start-execution --state-machine-arn $SM_ARN --name $EXECUTION_NAME`
	2. Show execution result: `aws stepfunctions describe-execution --execution-arn $EXECUTION_ARN`

## Cleanup
1. Delete the State Machine: `aws stepfunctions delete-state-machine --state-machine-arn $SM_ARN`
2. Delete the Role: 
	1. Detach policy: `aws iam detach-role-policy --role-name $SM_ROLE_NAME --policy-arn $POLICY_ARN`
	2. Delete the Role: `aws iam delete-role --role-name $SM_ROLE_NAME`
3. Close the terminal

## History
- 2025-12-08 success
