# 010-domain-lambda

## Task
Create a Domain Name for a Lambda Function.

## Steps
1. Create a Lambda Function
	1. Template: `Author from scratch`
	2. Function name: `kata-domain-lambda-function`
	2. Runtime: `Python`
	3. Architecture: `x86_64`
	4. Permissions:
		1. Execution role: `Create a new role with basic Lambda permissions`
	5. Function URL: 
		1. Enabled: true
		2. Auth type: `NONE`
	6. Test Function URL: `curl https://csi6auxnef3zv63m5jyjinypt40oqufv.lambda-url.us-east-1.on.aws/`
2. Create a Hosted Zone
	1. Domain name: `yaal.com`
	2. Type: `Public hosted zone`
	3. Test domain: `dig yaal.com`
3. Create a Record
	1. Record name: `lambda-1`
	2. Record type: `CNAME`
	3. Alias: false
	4. Value: Lambda URL `i5nyb6mm6havgsdx7tlzllgpha0myqzk.lambda-url.us-east-1.on.aws`
	5. Test the record: `dig lambda-1.yaal.com`
4. (NOT WORK: NEED API GATEWAY!!!!) Invoke Function through the Record: `curl https://lambda-1.yaal.com`

## Cleanup
1. Delete the Function
2. Delete the Execution Role
3. Delete the Hosted Zone
