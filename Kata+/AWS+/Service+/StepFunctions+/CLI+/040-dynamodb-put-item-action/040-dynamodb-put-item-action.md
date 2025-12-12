# 040-dynamodb-put-item-action

## Task
Create a StateMachine that puts and gets an Item from a DynamoDB table.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export TABLE=kata-table-dynamodb-put-item-action
	export SM_ROLE_NAME=kata-role-dynamodb-put-item-action
	export SM_ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$SM_ROLE_NAME
	export POLICY_ARN=arn:aws:iam::aws:policy/AWSStepFunctionsFullAccess
	export SM_NAME=kata-sm-dynamodb-put-item-action
	export SM_ARN=arn:aws:states:us-east-1:$ACCOUNT:stateMachine:$SM_NAME
	export EXECUTION_NAME=execution1
	export EXECUTION_ARN=arn:aws:states:us-east-1:$ACCOUNT:execution:$SM_NAME:$EXECUTION_NAME
	```
4. Create Table
   1. Create a Table
      ```shell
      aws dynamodb create-table \
         --table-name $TABLE \
         --attribute-definitions AttributeName=id,AttributeType=S \
         --key-schema AttributeName=id,KeyType=HASH \
         --billing-mode PAY_PER_REQUEST
      ```
   2. Wait for creation: `aws dynamodb wait table-exists --table-name $TABLE`
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
6. Test:
	1. Execute the State Machine:
		1. Start execution: `aws stepfunctions start-execution --state-machine-arn $SM_ARN --name $EXECUTION_NAME`
		2. Show execution result: `aws stepfunctions describe-execution --execution-arn $EXECUTION_ARN`
	2. Receive message from the Queue: `aws sqs receive-message --queue-url $Q_ARN`

## Cleanup
1. Delete the State Machine: 
	1. Delete the State Machine: `aws stepfunctions delete-state-machine --state-machine-arn $SM_ARN`
	2. Delete the Role: 
		1. Detach policy: `aws iam detach-role-policy --role-name $SM_ROLE_NAME --policy-arn $POLICY_ARN`
		2. Delete inline policy: `aws iam delete-role-policy --role-name $SM_ROLE_NAME --policy-name policy1`
		3. Delete the Role: `aws iam delete-role --role-name $SM_ROLE_NAME`
2. Delete table: `aws dynamodb delete-table --table-name $TABLE`
3. Close terminal

## History
- 2025-12-12 success
