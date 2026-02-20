# 020-logs

## Task
See logs of a REST API.

## Steps
1. Open a new terminal
2. Change the current directory
3. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export API_NAME=kata-api-logs
	export STAGE_NAME=test
	export ROLE_NAME=kata-role-logs
	export ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$ROLE_NAME
	export POLICY=arn:aws:iam::aws:policy/service-role/AmazonAPIGatewayPushToCloudWatchLogs
	```
4. Configure "CloudWatch log role ARN" (per Account):
	1. Create a Role: `aws iam create-role --role-name $ROLE_NAME --assume-role-policy-document file://trust-policy.json`
	2. Attach Policy: `aws iam attach-role-policy --role-name $ROLE_NAME --policy-arn $POLICY`
	3. Backup current value: `aws apigateway get-account --query cloudwatchRoleArn`
	4. Set role: `aws apigateway update-account --patch-operations op=replace,path=/cloudwatchRoleArn,value=$ROLE_ARN`
5. Create a REST API: 
	1. Create API: `aws apigateway create-rest-api --name $API_NAME`
	2. Get API ID: `export API_ID=$(aws apigateway get-rest-apis --query "items[?name=='$API_NAME'].id" --output text)`
	3. Create a Method
		1. Get the Resource ID: 
			```shell
			export RESOURCE_ID=$(aws apigateway get-resources --rest-api-id $API_ID \
				--query "items[?path=='/'].id" --output text)
			```
    	2. Create Method: 
    		```shell
    		aws apigateway put-method --rest-api-id $API_ID --resource-id $RESOURCE_ID \
    			--http-method GET --authorization-type NONE
    		```
    	3. Create a Method Response:
			```shell
			aws apigateway put-method-response --rest-api-id $API_ID --resource-id $RESOURCE_ID \
	  			--http-method GET --status-code 200 \
	  			--response-models '{"application/json":"Empty"}'
			```
		4. Create a Mock Integration:
			1. Create Integration:
				```shell
				aws apigateway put-integration --rest-api-id $API_ID --resource-id $RESOURCE_ID \
					--http-method GET --type MOCK \
					--request-templates '{"application/json":"{\"statusCode\": 200}"}'
				```
			2. Create an Integration Response:
				```shell
				aws apigateway put-integration-response --rest-api-id $API_ID --resource-id $RESOURCE_ID \
					--http-method GET --status-code 200 \
					--response-templates '{"application/json":"{\"message\": \"This is a mock response\"}"}'
				```
	4. Deploy API: `aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME`
6. Enable logging for Stage:
	```shell
	aws apigateway update-stage \
		--rest-api-id $API_ID \
		--stage-name $STAGE_NAME \
		--patch-operations \
			op=replace,path=/*/*/logging/loglevel,value=INFO \
			op=replace,path=/*/*/logging/dataTrace,value=true \
			op=replace,path=/*/*/metrics/enabled,value=true
	```
7. Test API
	1. Get Region: `export REGION=$(aws configure get region)`
	2. Constract Invoke URL: `export STAGE_URL="https://$API_ID.execute-api.$REGION.amazonaws.com/$STAGE_NAME"`
	3. Invoke Stage: `curl -i $STAGE_URL`
8. Show logs
	1. Find the Log Group: `export LOG_GROUP="API-Gateway-Execution-Logs_$API_ID/$STAGE_NAME"`
	2. Show: `aws logs tail --since 1h $LOG_GROUP`

## Cleanup
1. Delete the API: `aws apigateway delete-rest-api --rest-api-id $API_ID`
2. Delete the Log Group:
	1. Unset "CloudWatch log role ARN":
		`aws apigateway update-account --patch-operations op=replace,path=/cloudwatchRoleArn,value=""`
	2. Delete the Role:
		```shell
		aws iam detach-role-policy --role-name $ROLE_NAME --policy $POLICY
		aws iam delete-role --role-name $ROLE_NAME
		```
	3. Delete the Log Group: `aws logs delete-log-group --log-group-name $LOG_GROUP`
3. Close the terminal

## History
- 2026-02-22 success
