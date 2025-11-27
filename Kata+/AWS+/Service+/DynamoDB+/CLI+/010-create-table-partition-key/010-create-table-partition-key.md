# 010-create-table-partition-key

## Task
Create a DynamoDB table having a Parition Key only. Put, get, and delete an item.

## Steps
1. Set environment variables
   ```shell
   set -x
   export TABLE=kata-table-create-table-partition-key
   ```
2. Create Table
   1. Create a Table
      ```shell
      aws dynamodb create-table \
         --table-name $TABLE \
         --attribute-definitions AttributeName=id,AttributeType=S \
         --key-schema AttributeName=id,KeyType=HASH \
         --billing-mode PAY_PER_REQUEST
      ```
   2. Wait for creation: `aws dynamodb wait table-exists --table-name $TABLE`
3. Use the table
   1. Write an Item:
      ```shell
      aws dynamodb put-item --table-name $TABLE \
         --item '{ "id": {"S": "john"}, "firstName": {"S": "John"}, "age": {"N": "30"} }'
      ```
   2. Scan all items: `aws dynamodb scan --table-name $TABLE`
   3. Get the item by primary key: `aws dynamodb get-item --table-name $TABLE --key '{"id": {"S": "john"}}'`
   4. Query items by partition key: 
      ```shell
      aws dynamodb query --table-name $TABLE \
         --key-condition-expression "id = :idValue" \
         --expression-attribute-values '{":idValue":{"S":"john"}}'
      ```
   5. Delete an item: `aws dynamodb delete-item --table-name $TABLE --key '{"id": {"S": "john"}}'`

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name $TABLE`
2. Unset env vars: `set +x; unset TABLE`

## History
- 2025-11-02 success
