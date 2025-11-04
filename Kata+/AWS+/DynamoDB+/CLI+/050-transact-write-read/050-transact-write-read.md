# 050-transact-write-read

## Task
Perform transactional write on a DynamoDB table. 

## Steps
1. Change current dir
2. Set environment variables
    ```shell
    set -x
    export TABLE=kata-table-transact-write-read
    ```
3. Create table
   1. Create a Table
      ```bash
      aws dynamodb create-table \
          --table-name $TABLE \
          --attribute-definitions AttributeName=id,AttributeType=S \
          --key-schema AttributeName=id,KeyType=HASH \
          --billing-mode PAY_PER_REQUEST
      ```
   2. Wait for creation: `aws dynamodb wait table-exists --table-name $TABLE`
4. Transact write items: `aws dynamodb transact-write-items --transact-items file://transact-write.json`
5. List items: `aws dynamodb scan --table-name $TABLE`
6. Transact read items: `aws dynamodb transact-get-items --transact-items file://transact-get.json`

## Cleanup
1. Delete table:  `aws dynamodb delete-table --table-name $TABLE`
2. Unset env vars: `set +x; unset TABLE`

## History
- 2025-11-02 success
