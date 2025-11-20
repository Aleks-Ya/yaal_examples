# 060-local-index

## Task
Create a Local Secondary Index (LSI) for a DynamoDB table.

## Steps
1. Change current dir
2. Set environment variables
   ```shell
   set -x
   export TABLE=kata-table-local-index
   ```
3. Create Table
   1. Create a Table
      ```bash
      aws dynamodb create-table \
         --table-name $TABLE \
         --attribute-definitions \
            AttributeName=customer,AttributeType=S \
            AttributeName=saleId,AttributeType=N \
            AttributeName=product,AttributeType=S \
            AttributeName=manager,AttributeType=S \
         --key-schema \
            AttributeName=customer,KeyType=HASH \
            AttributeName=saleId,KeyType=RANGE \
         --billing-mode PAY_PER_REQUEST \
         --local-secondary-indexes '[
            {
               "IndexName": "CustomersByProduct",
               "KeySchema": [
                  {"AttributeName": "customer", "KeyType": "HASH"},
                  {"AttributeName": "product", "KeyType": "RANGE"}
               ],
               "Projection": {
                  "ProjectionType": "ALL"
               }
            },
            {
               "IndexName": "CustomersByManager",
               "KeySchema": [
                  {"AttributeName": "customer", "KeyType": "HASH"},
                  {"AttributeName": "manager", "KeyType": "RANGE"}
               ],
               "Projection": {
                  "ProjectionType": "ALL"
               }
            }
         ]'
      ```
   2. Wait for creation: `aws dynamodb wait table-exists --table-name $TABLE`
4. Write Items: `aws dynamodb batch-write-item --request-items file://batch-write.json`
5. Read Items:
   1. Scan all items: `aws dynamodb scan --table-name $TABLE`
   2. Query items by cusotmer (Base Table): 
      ```shell
      aws dynamodb query --table-name $TABLE \
         --key-condition-expression "customer = :customerValue" \
         --expression-attribute-values '{":customerValue":{"S":"John"}}'
      ```
   3. Query items by product (`CustomersByProduct` LSI): 
      ```shell
      aws dynamodb query --table-name $TABLE \
         --index-name CustomersByProduct \
         --key-condition-expression "customer = :customerValue AND product = :productValue" \
         --expression-attribute-values '{
            ":customerValue": {"S":"John"},
            ":productValue": {"S":"Book"}
         }'
      ```
   4. Query items by manager (`CustomersByManager` LSI): 
      ```shell
      aws dynamodb query --table-name $TABLE \
         --index-name CustomersByManager \
         --key-condition-expression "customer = :customerValue AND manager = :managerValue" \
         --expression-attribute-values '{
            ":customerValue": {"S":"John"},
            ":managerValue": {"S":"Mark"}
         }'
      ```

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name $TABLE`
2. Unset env vars: `set +x; unset TABLE`

## History
- 2025-11-20 success
