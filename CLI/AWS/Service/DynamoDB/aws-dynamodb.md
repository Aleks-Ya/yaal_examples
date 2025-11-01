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
Wait for table creation: `aws dynamodb wait table-exists --table-name table1`

## Item
Create an item: `aws dynamodb put-item --table-name table1 --item '{ "id": {"S": "john"}, "firstName": {"S": "John"}, "age": {"N": "30"} }'`
List all items in a table: `aws dynamodb scan --table-name table1`
Get an item by primary key: `aws dynamodb get-item --table-name table1 --key '{"id": {"S": "john"}}'`
Get an item by partition key:
```shell
aws dynamodb query --table-name table1 \
    --key-condition-expression "id = :idValue" \
    --expression-attribute-values '{":idValue":{"S":"john"}}'
```
Delete an item: `aws dynamodb delete-item --table-name table1 --key '{"id": {"S": "12345"}}'`

## Batch
Batch write items: `aws dynamodb batch-write-item --request-items file://batch-write.json`
Batch get items: `aws dynamodb batch-get-item --request-items file://batch-get.json`
Batch delete times: `aws dynamodb batch-write-item --request-items file://batch-delete.json`

## Transaction
Transactional write items: `aws dynamodb transact-write-items --transact-items file://transact-items.json`
