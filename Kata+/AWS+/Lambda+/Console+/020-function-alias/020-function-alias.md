# 020-function-alias

## Task
Status: success
Create a function with 3 environments: DEV, TST, PRD and deploy different versions to them.

## Setup
1. Create a function
	1. Author from scratch
	2. Function name: `kata-function-function-alias`
	3. Runtime: `Python`
2. Update code:
   1. Code:
      ```python
		def lambda_handler(event, context):
			return "Hello"
      ```
   2. Deploy
   3. Create a test event
       1. Event JSON: `{}`
   4. Execute the test
3. Create versions
	1. Create `DEV` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, DEV!"
			```
		2. Deploy
		3. Test
		4. Publish new version
	2. Create `TST` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, TST!"
			```
		2. Deploy
		3. Test
		4. Publish new version
	3. Create `PRD` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, PRD!"
			```
		2. Deploy
		3. Test
		4. Publish new version
4. Create aliases
	1. Alias `DEV`
		1. Name: `DEV`
		2. Version: `1`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test: open URL in browser
	2. Alias `TST`
		1. Name: `TST`
		2. Version: `2`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test: open URL in browser
	3. Alias `PRD`
		1. Name: `PRD`
		2. Version: `3`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test: open URL in browser
5. Update `TST` version
	1. Code:
		```python
		def lambda_handler(event, context):
			return "Hello, TST #2!"
		```
	2. Deploy
	3. Test
	4. Publish new version
	5. Switch alias `TST` to version `4`
	6. Test: open URL in browser

## Cleanup
1. Delete function `kata-function-function-alias`

## History
- 2025-10-14 success
