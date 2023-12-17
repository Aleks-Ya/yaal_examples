# 01-domain-lambda

## Task
Create a Domain Name for a Lambda Function.

## Setup
1. Create a Lambda Function
	1. Template: `Author from scratch`
	2. Function name: `function-1`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
	5. Enable function URL: `NONE`
2. Create a Hosted Zone
	1. Domain name: `yaal.com`
	2. Type: `Public hosted zone`
3. Create a Record
	1. Record name: `lambda-1`
	2. Record type: `CNAME`
	3. Alias: false
	4. Value: Lambda URL `i5nyb6mm6havgsdx7tlzllgpha0myqzk.lambda-url.us-east-1.on.aws`



NOT FINISHED!!!! need use API Gateway

## Cleanup
