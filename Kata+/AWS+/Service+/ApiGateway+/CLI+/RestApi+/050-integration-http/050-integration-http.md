# 050-integration-http

## Task
Create a **REST** API with an HTTP integration.

## Steps
1. Open a new terminal
2. Set environment variables
	```shell
	set -x
	export API_NAME=kata-api-integration-http
	export STAGE_NAME=test
	export BACKEND=https://httpbin.io/uuid
	```
3. Create a REST API: 
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
    3. Create a Method Response:
		```shell
		aws apigateway put-method-response --rest-api-id $API_ID --resource-id $RESOURCE_ID \
	  		--http-method GET --status-code 200
		```
	4. Create an HTTP Integration:
		1. Create Integration:
			```shell
			aws apigateway put-integration --rest-api-id $API_ID --resource-id $RESOURCE_ID \
				--http-method GET --type HTTP_PROXY --integration-http-method GET --uri $BACKEND
			```
		2. Create an Integration Response:
			```shell
			aws apigateway put-integration-response --rest-api-id $API_ID --resource-id $RESOURCE_ID \
				--http-method GET --status-code 200
			```
5. Deploy API: `aws apigateway create-deployment --rest-api-id $API_ID --stage-name $STAGE_NAME`
6. Test API:
	1. Get Region: `export REGION=$(aws configure get region)`
	2. Constract Invoke URL: `export STAGE_URL="https://$API_ID.execute-api.$REGION.amazonaws.com/$STAGE_NAME"`
	3. Invoke Stage: `curl -i $STAGE_URL`

## Cleanup
1. Delete the API: `aws apigateway delete-rest-api --rest-api-id $API_ID`
2. Close the terminal

## History
- 2026-02-22 success
