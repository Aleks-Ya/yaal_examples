# 010-create-table

## Task
Status: ?
Create a DynamoDB table, put and get an item.

## Setup

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

## Cleanup

1. Delete table: `table-1`
    1. Delete all CloudWatch alarms for table-1: `true`
    2. Delete all CloudWatch alarms for table-1: `false`
