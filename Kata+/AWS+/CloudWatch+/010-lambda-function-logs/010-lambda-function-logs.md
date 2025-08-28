# 010-lambda-function-logs

## Task
Display logs produced by a Lambda Function.

## Setup
1. Create a Lambda Function
	1. Type: `Author from scratch`
	2. Function name: `function-1`
	3. Runtime: `Python`
	4. Code:
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
	4. Test the function
2. Find logs via Function
	1. Open `Monitor` tab in the Function
	2. Find section `CloudWatch Logs` and table `Recent invocations`
	3. In  the top row, click the link in column `LogStream`
	4. Click `Start tailing`
	5. Test the function to see new logs in the tailing
3. Find logs via CloudWatch
	1. Open CloudWatch page
	2. Click link `Log groups`
	3. Click link `/aws/lambda/function-1`
	4. In table `Log streams` click the top log stream
	5. Click `Start tailing`
	6. Test the function to see new logs in the tailing

## Cleanup
1. Delete the function