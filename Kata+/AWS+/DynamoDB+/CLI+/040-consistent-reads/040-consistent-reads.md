# 040-consistent-reads

## Task
Demonstrate Eventually and Stringly consistent reads.

## Setup
1. Create a table
    ```bash
    aws dynamodb create-table \
        --table-name consistent-reads-table-1 \
        --attribute-definitions AttributeName=id,AttributeType=S \
        --key-schema AttributeName=id,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
    ```
2. Create an item
	1. Put the item
	```bash
	aws dynamodb put-item \
		--table-name consistent-reads-table-1 \
		--item '{ "id": {"S": "1"}, "name": {"S": "John"} }'
	```
	2. Read the item:
		```bash
		aws dynamodb get-item \
			--table-name consistent-reads-table-1 \
			--key '{"id": {"S": "1"}}'
		```
3. Demonstrate Eventually consistent read
	1. Update item: 
		```bash
		aws dynamodb put-item \
			--table-name consistent-reads-table-1 \
			--item '{ "id": {"S": "1"}, "name": {"S": "Ann"} }'
		```
	2. Read item (consume 0.5 WCU):
		```bash
		aws dynamodb get-item \
			--table-name consistent-reads-table-1 \
			--key '{"id": {"S": "1"}}' \
			--return-consumed-capacity TOTAL
		```
4. Demonstrate Strongly consistent read
	1. Update item: 
		```bash
		aws dynamodb put-item \
			--table-name consistent-reads-table-1 \
			--item '{ "id": {"S": "1"}, "name": {"S": "Mary"} }'
		```
	2. Read item (consume 1 WCU):
		```bash
		aws dynamodb get-item \
			--table-name consistent-reads-table-1 \
			--key '{"id": {"S": "1"}}' \
			--consistent-read \
			--return-consumed-capacity TOTAL
		```

## Cleanup
1. Delete table: `aws dynamodb delete-table --table-name consistent-reads-table-1`

## History
- 2024-01-10 success
