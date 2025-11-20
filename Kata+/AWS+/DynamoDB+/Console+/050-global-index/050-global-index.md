# 050-global-index

## Task
Use a Global Secondary Index (GSI) for improving requests.

## Steps
1. Create a table
    1. Table name: `global-index-table-1`
    2. Partition key: `country`, String
    3. Sort key: `company`, String
    4. Table settings: `Customize settings`
    	1. Create a global index
			1. Partition key: `company`, String
			2. Sort key: `director`, String
			3. Index name: `company-director-index`
			4. Attribute projections: `All`
2. Write items: `aws dynamodb batch-write-item --request-items file://items.json`
3. Test
	1. Query to the table
		1. Select a table or index: `global-index-table-1`
		2. country (Partition key): `France`
	2. Query to the index
		1. Select a table or index: `company-director-index`
		2. company: `Michelin`

## Cleanup
1. Delete table `global-index-table-1`

## History
- 2024-01-10 success
