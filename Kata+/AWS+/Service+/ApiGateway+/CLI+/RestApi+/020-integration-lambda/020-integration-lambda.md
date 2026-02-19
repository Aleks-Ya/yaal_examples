# 020-integration-lambda

## Task
Create a REST API with a Lambda Function integration.

## Steps
1. Open a new terminal
2. Change the current directory
3. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export REGION=$(aws configure get region)
	export ROLE=kata-role-integration-lambda
	export F_NAME=kata-f-integration-lambda
	export F_ARN=arn:aws:lambda:$REGION:$ACCOUNT:function:$F_NAME
	export API_NAME=kata-api-integration-lambda
	export STAGE_NAME=test
	```
4. Create a Lambda Function
	1. Create an Execution Role:
		`aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Create a Deployment Package: `zip deployment-package.zip handler.py`
	3. Create a function:
		```shell
		aws lambda create-function \
			--function-name $F_NAME \
			--runtime python3.14 \
			--role arn:aws:iam::$ACCOUNT:role/$ROLE\
			--handler handler.lambda_handler \
			--zip-file fileb://deployment-package.zip
		```
	4. Wait: `aws lambda wait function-active --function-name $F_NAME`
	5. Test function: `aws lambda invoke --function-name $F_NAME /dev/stdout`
5. Create a REST API: 
	1. Create API: `aws apigateway create-rest-api --name $API_NAME`
	2. Get API ID: `export API_ID=$(aws apigateway get-rest-apis --query "items[?name=='$API_NAME'].id" --output text)`
4. Create a Method
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
	4. Create an Integration:
		1. Create Integration:
			```shell
			aws apigateway put-integration \
				--rest-api-id $API_ID \
				--resource-id $RESOURCE_ID \
				--http-method GET \
				--type AWS_PROXY \
				--integration-http-method POST \
				--uri "arn:aws:apigateway:$REGION:lambda:path/2015-03-31/functions/$F_ARN/invocations"
			```
		2. (?) Grant permissions:
			```shell
			aws lambda add-permission \
			  --function-name "$F_NAME" \
			  --statement-id "apigw-${API_ID}-get-3" \
			  --action lambda:InvokeFunction \
			  --principal apigateway.amazonaws.com \
			  --source-arn "$F_ARN/*"

			  --source-arn "arn:aws:execute-api:$REGION:$ACCOUNT_ID:$API_ID/*/GET/*"
			```
5. Deploy API: `aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME`
6. Test API
	1. Constract Invoke URL: `export STAGE_URL="https://$API_ID.execute-api.$REGION.amazonaws.com/$STAGE_NAME"`
	2. Invoke Stage: `curl -i $STAGE_URL`

## Cleanup
1. Delete the API: `aws apigateway delete-rest-api --rest-api-id $API_ID`
2. Delete the Function
2. Close the terminal

## History