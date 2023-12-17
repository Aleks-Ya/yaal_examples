# 01-create-table

## Task

Create a DynamoDB table, put and get an item.

Variants:

1. Use AWS Console
2. Use AWS CLI

## AWS Console variant

### Setup

1. Create a Table
    1. Table name: `table-1`
    2. Partition key: `id`, String
    3. Table settings: `Default settings`
2. Create item:
    1. Attribute `id`: `john`
    2. String `firstName`: `John`
    3. Number `age`: `30`
3. Scan or query items
    1. Type: `Query`
    2. id (Partition key): `john`

### Cleanup

1. Delete table: `table-1`
    1. Delete all CloudWatch alarms for table-1: `true`
    2. Delete all CloudWatch alarms for table-1: `false`

## AWS CLI variant

### Setup

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

### Cleanup

1. Delete table: `aws dynamodb delete-table --table-name table-2`
