# 020-function-alias

## Task
Create a function with 2 environments (`DEV` and `PRD`) and deploy different versions to them.

## Steps
1. Create a function
	1. Author from scratch
	2. Function name: `kata-f-function-alias`
	3. Runtime: `Python`
	4. Architecture: `x86_64`
	5. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
2. Update code:
   1. Code:
      ```python
		def lambda_handler(event, context):
			return "Hello"
      ```
   2. Deploy
   3. Create a test event
       1. Name: `te1`
       2. Invocation type: `Synchronous`
       3. Event JSON: `{}`
   4. Execute the test event
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
			1. Version description: empty
	2. Create `PRD` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, PRD!"
			```
		2. Deploy
		3. Test
		4. Publish new version
			1. Version description: empty
4. Create aliases
	1. Alias `DEV`
		1. Name: `DEV`
		2. Version: `1`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test: open URL in browser
	2. Alias `PRD`
		1. Name: `PRD`
		2. Version: `2`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test: open URL in browser
5. Update `DEV` version
	1. Code:
		```python
		def lambda_handler(event, context):
			return "Hello, DEV #2!"
		```
	2. Deploy
	3. Test
	4. Publish new version
		1. Version description: empty
	5. Switch alias `DEV` to version `3`
	6. Test: open URL in browser

## Cleanup
1. Delete function `kata-f-function-alias`
2. Delete execution role `kata-f-create-function-role-`

## History
- 2025-10-14 success
- 2025-12-29 success
