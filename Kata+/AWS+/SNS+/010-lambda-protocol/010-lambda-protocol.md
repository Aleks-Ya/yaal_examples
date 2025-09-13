# 010-lambda-protocol

## Task
Create a Topic that delivers messages to a Lambda Function.

## Setup
1. Create Function
	1. Template: `Author from scratch`
	2. Function name: `kata-lambda-protocol-function`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
2. Test Function
	1. Start tailing Function logs in CloudWatch
	2. Send a test event
	3. See the test event in the log tail
3. Create Topic
	1. Type: `Standard`
	2. Name: `kata-lambda-protocol-topic`
4. Create Subscription
	1. Protocol: `AWS Lambda`
	2. Endpoint: function `kata-lambda-protocol-function`
5. Test Topic
	1. Publish a test message
	2. See log in the Function log tail

## Cleanup
1. Delete Subscription
2. Delete Topic
3. Delete Function
	1. Delete Function `kata-lambda-protocol-function`
	2. Delete Policy (?) `kata-lambda-protocol-function-0emvw64d`
	3. Delete LogGroup `/aws/lambda/kata-lambda-protocol-function`
