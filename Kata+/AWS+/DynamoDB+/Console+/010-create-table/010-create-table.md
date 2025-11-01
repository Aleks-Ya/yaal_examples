# 010-create-table

## Task
Create a DynamoDB table, put and get an item.

## Setup
1. Create a Table
    1. Table name: `kata-table-create-table`
    2. Partition key: `id`, String
    3. Table settings: `Default settings`
2. Create item:
    1. Attribute `id`: `john`
    2. String `firstName`: `John`
    3. Number `age`: `30`
3. Scan or query items
    1. Type: `Query`
    2. id (Partition key): `john`

## Cleanup
1. Delete table: `kata-table-create-table`
    1. Delete all CloudWatch alarms for `kata-table-create-table`: `true`
    2. Create an on-demand backup of `kata-table-create-table`: `false`

## History
- 2025-11-02 success
