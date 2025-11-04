# 060-local-index

## Task
Create two Local Secondary Indices with sorting by different attributes.

## Steps
1. Create a table
    1. Table name: `table-1`
    2. Partition key: `id`, String
    3. Sort key: `name`, String
    4. Table settings: `Customize settings`
		1. Create local index #1
			1. Sort key: `age`
			2. Data type: `Number`
			3. Index name: `age-index`
			4. Attribute projections: `All`
		2. Create local index #1
			1. Sort key: `city`
			2. Data type: `String`
			3. Index name: `city-index`
			4. Attribute projections: `All`
2. Put items
	1. Item #1
		1. id: `1`
		2. name: `John`
		3. age: `30`
		4. city: `London`
	2. Item #2
		1. id: `1`
		2. name: `Mary`
		3. age: `25`
		4. city: `Berlin`
	3. Item #3
		1. id: `1`
		2. name: `Mark`
		3. age: `20`
		4. city: `Paris`
3. Test
	1. Sort by name (sorting key): 
		1. Type: `Query`
		2. Select a table or index: `table-1`
		3. id (Partition key): `1`
	2. Sort by age: 
		1. Type: `Query`
		2. Select a table or index: `age-index`
		3. id (Partition key): `1`
	3. Sort by city: 
		1. Type: `Query`
		2. Select a table or index: `city-index`
		3. id (Partition key): `1`

## Cleanup
1. Delete table `table-1`

## History
- 2024-01-08 success
