# 060-logs

## Task
Print logs from a Lambda Function.

## Steps
1. Open a new terminal
2. Change the current directory
3. Set environment variables: `source envs.sh`
4. Create an Execution Role:
	1. Create: 
		`aws iam create-role --role-name $ROLE_NAME --assume-role-policy-document file://trust-policy.json`
	2. Grant permissions: `aws iam attach-role-policy --role-name $ROLE_NAME --policy-arn $POLICY`
5. Create a Deployment Package: `zip deployment-package.zip handler.py`
6. Create a Function:
	1. Create:
		```shell
		aws lambda create-function \
			--function-name $FUNCTION \
			--runtime python3.14 \
			--role $ROLE_ARN \
			--handler handler.lambda_handler \
			--zip-file fileb://deployment-package.zip
		```
	2. Wait: `aws lambda wait function-active --function-name $FUNCTION`
	3. Invoke function: `aws lambda invoke --function-name $FUNCTION /dev/stdout`
7. Show logs: `aws logs tail --since 1h $LOG_GROUP`

## Cleanup
1. Delete Function: `aws lambda delete-function --function-name $FUNCTION`
2. Delete Execution Role: 
	```shell
	aws iam detach-role-policy --role-name $ROLE_NAME --policy $POLICY
	aws iam delete-role --role-name $ROLE_NAME
	```
3. Delete the Log Group: `aws logs delete-log-group --log-group-name $LOG_GROUP`
4. Close the terminal

# History
- 2026-02-22 success
