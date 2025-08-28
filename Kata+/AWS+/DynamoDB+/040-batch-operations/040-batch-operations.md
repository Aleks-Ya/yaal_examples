# 040-batch-operations

## Task
Use `BatchGetItem` and `BatchWriteItem` against a DynamoDB table.

## Setup
1. Create a table:
    ```bash
    aws dynamodb create-table \
        --table-name batch-operations-table-1 \
        --attribute-definitions AttributeName=id,AttributeType=S \
        --key-schema AttributeName=id,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
    ```
2. Write items: `aws dynamodb batch-write-item --request-items file://batch-put.json`
3. Read items: `aws dynamodb batch-get-item --request-items file://batch-read.json`
4. Delete items: `aws dynamodb batch-write-item --request-items file://batch-delete.json`
5. List items: `aws dynamodb scan --table-name batch-operations-table-1`

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name batch-operations-table-1`

## History
1. 2024-01-07
