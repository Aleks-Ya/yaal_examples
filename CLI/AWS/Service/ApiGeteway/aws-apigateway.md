# API Gateway CLI (for REST API)

## API
List REST APIs: `aws apigateway get-rest-apis`
Create a REST API: `aws apigateway create-rest-api --name api1`
Delete a REST API: `aws apigateway delete-rest-api --rest-api-id klut450z55`
Get API ID by name: `aws apigateway get-rest-apis --query "items[?name=='api1'].id" --output text`

## Stage
List Stages: `aws apigateway get-stages --rest-api-id ab12cd34ef`
