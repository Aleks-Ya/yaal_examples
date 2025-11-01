# 030-exceed-throughput-capacity

## Task
Overload table read and write throughput capacity (write and read operations consume ≥1 WCU/RCU).

## Setup
1. Change current dir
2. Set environment variables
    ```shell
    set -x
    export TABLE=kata-table-exceed-throughput-capacity
    ```
3. Create a table:
    ```bash
    aws dynamodb create-table \
        --table-name $TABLE \
        --attribute-definitions AttributeName=id,AttributeType=S \
        --key-schema AttributeName=id,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
    ```
4. Write items: `aws dynamodb batch-write-item --return-consumed-capacity TOTAL --request-items file://batch-write.json`
5. Get items: `aws dynamodb batch-get-item --return-consumed-capacity TOTAL --request-items file://batch-get.json`
6. Trying batch: `./batch-write.sh`

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name $TABLE`
2. Unset env vars: `set +x; unset TABLE1 TABLE2`

## History
- 2024-01-07 success
- 2025-11-02 fail (can't reproduce exceeding)