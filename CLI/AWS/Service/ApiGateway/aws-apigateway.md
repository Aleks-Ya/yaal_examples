# API Gateway CLI (for REST API)

## API
List REST APIs: `aws apigateway get-rest-apis`
Create a REST API: `aws apigateway create-rest-api --name api1`
Delete a REST API: `aws apigateway delete-rest-api --rest-api-id klut450z55`
Get API ID by name: `aws apigateway get-rest-apis --query "items[?name=='api1'].id" --output text`

## Stage
List Stages: `aws apigateway get-stages --rest-api-id ab12cd34ef`
Show Stage details: `aws apigateway get-stage --rest-api-id ab12cd34ef --stage-name prd`
Flush a Stage cache: `aws apigateway flush-stage-cache --rest-api-id ab12cd34ef --stage-name prd`

## API Key
List API Keys: `aws apigateway get-api-keys`
Show API Key details: `aws apigateway get-api-key --api-key ay7epe5eda`
Show API Key details (with value): `aws apigateway get-api-key --include-value --api-key ay7epe5eda`
Create an API Key: `aws apigateway create-api-key --enabled --name key1`
Delete an API Key: `aws apigateway delete-api-key --api-key el964rceh5`

## Usage Plan
List Usage Plans: `aws apigateway get-usage-plans`
List API Keys associated to a Usage Plan: `aws apigateway get-usage-plan-keys --usage-plan-id xuqdj2`
Associate an API Key with a Usage Plan:
	`aws apigateway create-usage-plan-key --key-type API_KEY --usage-plan-id xuqdj2 --key-id moox37mhw7`
Create a Usage Plan: `aws apigateway create-usage-plan --name plan1`

## Resource
List all resources in a REST API: `aws apigateway get-resources --embed methods --rest-api-id klut450z55`

## Account
Get current "CloudWatch log role ARN": `aws apigateway get-account --query cloudwatchRoleArn`
Set "CloudWatch log role ARN": 
```shell
aws apigateway update-account \
  --patch-operations op=replace,path=/cloudwatchRoleArn,value=arn:aws:iam::123456789012:role/APIGatewayCloudWatchLogsRole
```
Unset "CloudWatch log role ARN": `aws apigateway update-account --patch-operations op=replace,path=/cloudwatchRoleArn,value=""`
