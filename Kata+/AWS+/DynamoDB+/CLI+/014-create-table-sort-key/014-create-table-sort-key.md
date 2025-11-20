# 014-create-table-sort-key

## Task
Create a DynamoDB table having both a Partition Key and a Sort Key.

## Steps
1. Set environment variables
   ```shell
   set -x
   export TABLE=kata-table-create-table-sort-key
   ```
2. Create Table
   1. Create a Table
      ```shell
      aws dynamodb create-table \
         --table-name $TABLE \
         --attribute-definitions AttributeName=customer,AttributeType=S AttributeName=order,AttributeType=N \
         --key-schema AttributeName=customer,KeyType=HASH AttributeName=order,KeyType=RANGE \
         --billing-mode PAY_PER_REQUEST
      ```
   2. Wait for creation: `aws dynamodb wait table-exists --table-name $TABLE`
3. Use the table
   1. Write Items:
      ```shell
      aws dynamodb put-item --table-name $TABLE --item '{ "customer": {"S": "John"}, "order": {"N": "1"}, "product": {"S": "car"} }'
      aws dynamodb put-item --table-name $TABLE --item '{ "customer": {"S": "John"}, "order": {"N": "2"}, "product": {"S": "motorcycle"} }'
      aws dynamodb put-item --table-name $TABLE --item '{ "customer": {"S": "Mary"}, "order": {"N": "1"}, "product": {"S": "dress"} }'
      ```
   2. Scan all items: `aws dynamodb scan --table-name $TABLE`
   3. Get the item by primary key: `aws dynamodb get-item --table-name $TABLE --key '{"customer": {"S": "John"}, "order": {"N": "2"} }'`
   4. Query items by partition key: 
      ```shell
      aws dynamodb query --table-name $TABLE \
         --key-condition-expression "customer = :idValue" \
         --expression-attribute-values '{":idValue":{"S":"John"}}'
      ```
   5. Delete an item by primary key: `aws dynamodb delete-item --table-name $TABLE --key '{"customer": {"S": "John"}, "order": {"N": "2"} }'`

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name $TABLE`
2. Unset env vars: `set +x; unset TABLE`

## History
- 2025-11-20 success
