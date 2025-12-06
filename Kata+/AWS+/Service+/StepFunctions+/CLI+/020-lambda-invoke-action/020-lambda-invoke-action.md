# 020-lambda-invoke-action

## Task
Create a StateMachine that triggers a Lambda Function.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export F_NAME=kata-f-lambda-invoke-action
	export F_ROLE=kata-role-lambda-invoke-action-f
	export SM_NAME=kata-sm-lambda-invoke-action
	export SM_ARN=arn:aws:states:us-east-1:$ACCOUNT:stateMachine:$SM_NAME
	export SM_ROLE_NAME=kata-role-lambda-invoke-action-sm
	export SM_ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$SM_ROLE_NAME
	export POLICY_ARN=arn:aws:iam::aws:policy/AWSStepFunctionsFullAccess
	export EXECUTION_NAME=execution1
	export EXECUTION_ARN=arn:aws:states:us-east-1:$ACCOUNT:execution:$SM_NAME:$EXECUTION_NAME
	```
4. Create a Lambda Function:
	1. Create an Execution Role:
		`aws iam create-role --role-name $F_ROLE --assume-role-policy-document file://function-trust-policy.json`
	2. Create a Deployment Package: `zip deployment-package.zip handler.py`
	3. Create a function:
		```shell
		aws lambda create-function \
			--function-name $F_NAME \
			--runtime python3.14 \
			--role arn:aws:iam::$ACCOUNT:role/$F_ROLE\
			--handler handler.lambda_handler \
			--zip-file fileb://deployment-package.zip
		```
	4. Test function: `aws lambda invoke --function-name $F_NAME /dev/stdout`
5. Create a State Machine:
	1. Create an execution role:
		1. Create a Role: 
			```shell
			aws iam create-role --role-name $SM_ROLE_NAME --assume-role-policy-document file://sm-trust-policy.json
			```
		2. Grant Step Functions permissions: 
			`aws iam attach-role-policy --role-name $SM_ROLE_NAME --policy-arn $POLICY_ARN`
		3. Grant Send Message permissions:
			```shell
			aws iam put-role-policy \
    			--role-name $SM_ROLE_NAME \
    			--policy-name policy1 \
    			--policy-document file://sm-policy.json
			```
	2. Create a State Machine:
		```shell
		aws stepfunctions create-state-machine \
			--name $SM_NAME \
			--definition file://sm.json \
			--role-arn $SM_ROLE_ARN
		```
	3. Execute:
		1. Start execution: `aws stepfunctions start-execution --state-machine-arn $SM_ARN --name $EXECUTION_NAME`
		2. Show execution result: `aws stepfunctions describe-execution --execution-arn $EXECUTION_ARN`

## Cleanup
1. Delete the State Machine: 
	1. Delete the State Machine: `aws stepfunctions delete-state-machine --state-machine-arn $SM_ARN`
	2. Delete the Role: 
		1. Detach policy: `aws iam detach-role-policy --role-name $SM_ROLE_NAME --policy-arn $POLICY_ARN`
		2. Delete inline policy: `aws iam delete-role-policy --role-name $SM_ROLE_NAME --policy-name policy1`
		3. Delete the Role: `aws iam delete-role --role-name $SM_ROLE_NAME`
2. Delete the Function:
	1. Delete the Function: `aws lambda delete-function --function-name $F_NAME`
	2. Delete the Role: `aws iam delete-role --role-name $F_ROLE`
	3. Delete deployment package: `rm deployment-package.zip`
3. Close the terminal

## History
- 2025-12-09 success
