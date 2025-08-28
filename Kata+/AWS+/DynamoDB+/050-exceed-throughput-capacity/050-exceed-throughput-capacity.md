# 050-exceed-throughput-capacity

## Task
Overload table read and write throughput capacity (write and read operations consume ≥1 WCU/RCU).

## Setup
1. Create a table:
    ```bash
    aws dynamodb create-table \
        --table-name overwhelm-capacity-table-1 \
        --attribute-definitions AttributeName=id,AttributeType=S \
        --key-schema AttributeName=id,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
    ```
2. Write items: `aws dynamodb batch-write-item --return-consumed-capacity TOTAL --request-items file://batch-put.json`
3. Read items: `aws dynamodb batch-get-item --return-consumed-capacity TOTAL --request-items file://batch-read.json`

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name overwhelm-capacity-table-1`

## History
1. 2024-01-07
