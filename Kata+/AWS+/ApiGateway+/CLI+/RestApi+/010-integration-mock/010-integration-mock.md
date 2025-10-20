# 010-integration-mock

## Task
Status: success
Create a REST API with a Mock integration.

## Setup
1. Set environment variables
```shell
export API_NAME=kata-api-integration-mock
export STAGE_NAME=test
```
2. Create a REST API: 
	1. Create API: `aws apigateway create-rest-api --name $API_NAME`
	2. Get API ID: `export API_ID=$(aws apigateway get-rest-apis --query "items[?name=='$API_NAME'].id" --output text)`
3. Create a Method
	1. Get the Resource ID: 
	`export RESOURCE_ID=$(aws apigateway get-resources --rest-api-id $API_ID --query "items[?path=='/'].id" --output text)`
    2. Create Method: 
    `aws apigateway put-method --rest-api-id $API_ID --resource-id $RESOURCE_ID --http-method GET --authorization-type NONE`
    3. Create a Method Response:
	```shell
	aws apigateway put-method-response \
	  	--rest-api-id $API_ID \
	  	--resource-id $RESOURCE_ID \
	  	--http-method GET \
	  	--status-code 200 \
	  	--response-models '{"application/json":"Empty"}'
	```
	4. Create a Mock Integration:
		1. Create Integration:
		```shell
		aws apigateway put-integration \
			--rest-api-id $API_ID \
			--resource-id $RESOURCE_ID \
			--http-method GET \
			--type MOCK \
			--request-templates '{"application/json":"{\"statusCode\": 200}"}'
		```
		2. Create an Integration Response:
		```shell
		aws apigateway put-integration-response \
			--rest-api-id $API_ID \
			--resource-id $RESOURCE_ID \
			--http-method GET \
			--status-code 200 \
			--selection-pattern "" \
			--response-templates '{"application/json":"{\"message\": \"This is a mock response\"}"}'
		```
5. Deploy API: `aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME`
6. Test API
	1. Get Region: `export REGION=$(aws configure get region)`
	2. Constract Invoke URL: `export STAGE_URL="https://$API_ID.execute-api.$REGION.amazonaws.com/$STAGE_NAME"`
	3. Invoke Stage: `curl -i $STAGE_URL`

## Cleanup
1. Delete the API: `aws apigateway delete-rest-api --rest-api-id $API_ID`
2. Unset env vars: `unset API_NAME STAGE_NAME API_ID RESOURCE_ID REGION STAGE_URL`

## History
- 2025-10-20 success
