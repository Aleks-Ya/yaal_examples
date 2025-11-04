# 020-batch-operations

## Task
Use batch write, get, and delete operations against two DynamoDB tables.

## Steps
1. Change current dir
2. Set environment variables
    ```shell
    set -x
    export TABLE1=kata-table-batch-operations-1
    export TABLE2=kata-table-batch-operations-2
    ```
3. Create tables
    1. Create Table1:
        ```bash
        aws dynamodb create-table \
            --table-name $TABLE1 \
            --attribute-definitions AttributeName=id,AttributeType=S \
            --key-schema AttributeName=id,KeyType=HASH \
            --billing-mode PAY_PER_REQUEST
        ```
    2. Create Table2:
        ```bash
        aws dynamodb create-table \
            --table-name $TABLE2 \
            --attribute-definitions AttributeName=city,AttributeType=S \
            --key-schema AttributeName=city,KeyType=HASH \
            --billing-mode PAY_PER_REQUEST
        ```
4. Batch write items: `aws dynamodb batch-write-item --request-items file://batch-write.json`
5. Batch get items: `aws dynamodb batch-get-item --request-items file://batch-get.json`
6. Batch delete items: `aws dynamodb batch-write-item --request-items file://batch-delete.json`
7. List items: 
    1. Table1: `aws dynamodb scan --table-name $TABLE1`
    2. Table2: `aws dynamodb scan --table-name $TABLE2`

## Cleanup
1. Delete tables: 
    1. Table1: `aws dynamodb delete-table --table-name $TABLE1`
    2. Table2: `aws dynamodb delete-table --table-name $TABLE2`
2. Unset env vars: `set +x; unset TABLE1 TABLE2`

## History
- 2024-01-07 success
- 2025-11-02 success
