# 010-create-table

## Task
Status: ?
Create a DynamoDB table, put and get an item.

## Setup

1. Create a Table
   ```bash
   aws dynamodb create-table \
       --table-name table-2 \
       --attribute-definitions AttributeName=id,AttributeType=S \
       --key-schema AttributeName=id,KeyType=HASH \
       --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
   ```
2. Write an Item:
   ```bash
   aws dynamodb put-item --table-name table-2 \
   --item '{ "id": {"S": "john"}, "firstName": {"S": "John"}, "age": {"N": "30"} }'
   ```
3. Read all items: `aws dynamodb scan --table-name table-2`
4. Get the item by primary key: `aws dynamodb get-item --table-name table-2 --key '{"id": {"S": "john"}}'`

## Cleanup

1. Delete table: `aws dynamodb delete-table --table-name table-2`
