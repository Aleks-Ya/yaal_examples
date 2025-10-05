# 020-function-alias

## Task
Status: ?
Create a function with 3 environments: DEV, TST, PRD and deploy different versions to them.

## Setup
1. Create a function
	1. Author from scratch
	2. Function name: `hello-1`
	3. Runtime: `Python`
2. Update code:
   1. Code:
      ```python
      def lambda_handler(event, context):
          return "Hello"
      ```
   2. Create a test event
       1. Event JSON: `{}`
   3. Execute the test
3. Create versions
	1. Create `DEV` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, DEV!"
			```
		2. Deploy
		3. Test
		4. Publish new version (`hello-1:1`)
	2. Create `TST` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, TST!"
			```
		2. Deploy
		3. Test
		4. Publish new version (`hello-1:2`)
	3. Create `DEV` version
		1. Update code:
			```python
			def lambda_handler(event, context):
				return "Hello, DEV!"
			```
		2. Deploy
		3. Test
		4. Publish new version (`hello-1:3`)
4. Create aliases
	1. Alias `DEV`
		1. Name: `DEV`
		2. Version: `1`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test with `curl`
	2. Alias `TST`
		1. Name: `TST`
		2. Version: `2`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test with `curl`
	3. Alias `PRD`
		1. Name: `PRD`
		2. Version: `3`
		3. Create Function URL
			1. Auth type: `NONE`
			2. Test with `curl`
5. Update `TST` version
	1. Code:
		```python
		def lambda_handler(event, context):
			return "Hello, TST #2!"
		```
	2. Deploy
	3. Test
	4. Publish new version (`hello-1:4`)
	5. Switch alias `TST` to version `4`
	6. Test with `curl`

## Cleanup
1. Remove function `hello-1`
