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
```bash
aws dynamodb create-table \
    --table-name MySampleTable \
    --attribute-definitions AttributeName=Id,AttributeType=S \
    --key-schema AttributeName=Id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```
Delete a table: `aws dynamodb delete-table --table-name MySampleTable`
Create an item: `aws dynamodb put-item --table-name table-1 --item '{ "id": {"S": "john"}, "firstName": {"S": "John"}, "age": {"N": "30"} }'`
List all items in a table: `aws dynamodb scan --table-name table-1`
Get an item by primary key: `aws dynamodb get-item --table-name table-1 --key '{"id": {"S": "john"}}'`

Batch write item: `aws dynamodb batch-write-item --request-items file://batch-write.json`
Batch get item: `aws dynamodb batch-get-item --request-items file://batch-read.json`
