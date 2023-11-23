# AWS DynamoDB CLI

## Local run
Run DynamoDB in Docker: `Database+/DynamoDB+/DynamoDbDocker/DynamoDB.md`
Configure local DynamoDB by default:
1. Option 1: `export AWS_ENDPOINT_URL_DYNAMODB=http://localhost:8000`
2. Option 2: configure `~/.aws/config`:
```
[default]
region = us-east-1
output = text
services = dynamodb1

[services dynamodb1]
dynamodb = 
  endpoint_url = http://localhost:8000
```
## Commands
List endpoints: `aws dynamodb describe-endpoints`
List tables: `aws dynamodb list-tables`
Create a table:
```
aws dynamodb create-table \
    --table-name MySampleTable \
    --attribute-definitions AttributeName=Id,AttributeType=S \
    --key-schema AttributeName=Id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```
Delete a table: `aws dynamodb delete-table --table-name MySampleTable`
