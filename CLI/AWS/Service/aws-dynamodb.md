# AWS DynamoDB CLI

Run DynamoDB in Docker: `Database+/DynamoDB+/DynamoDbDocker/DynamoDB.md`

## Endpoint
List endpoints: `aws dynamodb describe-endpoints`

## Table
List tables: `aws dynamodb list-tables`
Show details about a table: `aws dynamodb describe-table --table-name table1`
Create a table:
```bash
aws dynamodb create-table \
    --table-name table1 \
    --attribute-definitions AttributeName=Id,AttributeType=S \
    --key-schema AttributeName=Id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```
Delete a table: `aws dynamodb delete-table --table-name table1`
Show resoure-based policy: `aws dynamodb get-resource-policy --resource-arn arn:aws:dynamodb:us-east-1:523633434047:table/table1`

## Item
Create an item: `aws dynamodb put-item --table-name table-1 --item '{ "id": {"S": "john"}, "firstName": {"S": "John"}, "age": {"N": "30"} }'`
List all items in a table: `aws dynamodb scan --table-name table-1`
Get an item by primary key: `aws dynamodb get-item --table-name table-1 --key '{"id": {"S": "john"}}'`

## Batch
Batch write item: `aws dynamodb batch-write-item --request-items file://batch-write.json`
Batch get item: `aws dynamodb batch-get-item --request-items file://batch-read.json`
