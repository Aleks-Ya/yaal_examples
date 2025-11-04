# 010-lambda-function-logs

## Task
Status: success
Display logs produced by a Lambda Function.

## Steps
1. Create a Lambda Function
	1. Type: `Author from scratch`
	2. Function name: `kata-f-lambda-function-logs`
	3. Runtime: `Python`
	4. Execution role: `Create a new role with basic Lambda permissions`
	5. Code:
	```python
	import logging

	def lambda_handler(event, context):
	    logger = logging.getLogger()
	    logger.setLevel(logging.DEBUG)
	    logger.critical('Critical logging message')
	    logger.error('Error logging message')
	    logger.warning('Warning logging message')
	    logger.info('Info logging message')
	    logger.debug('Debug logging message')
	    return "Finished"
	```
	6. Deploy the function
	7. Test the function
2. Find logs via Function
	1. Open `Monitor` tab in the Function
	2. Find section `CloudWatch Logs` and table `Recent invocations`
3. Find logs via CloudWatch
	1. Open CloudWatch page
	2. Click link `Log groups`
	3. Click link `/aws/lambda/kata-f-lambda-function-logs`
	4. Click `Start tailing` at Log Group
	6. Test the function to see new logs in the tailing

## Cleanup
1. Delete the function: `kata-f-lambda-function-logs`
2. Delete the log group: `/aws/lambda/kata-f-lambda-function-logs`
3. Delete the execution role: `kata-f-lambda-function-logs-role-`

## History
- 2025-10-23 success
