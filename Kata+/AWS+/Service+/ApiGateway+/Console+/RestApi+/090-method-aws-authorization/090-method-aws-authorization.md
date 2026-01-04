# 090-method-aws-authorization

## Task
Require AWS IAM authorization to invoke a method in a REST API.

## Steps
1. Create an API
	1. Build a REST API
    	1. Type: `New API`
    	2. Name: `kata-api-method-aws-authorization`
    	3. API endpoint type: `Regional`
    	4. Security policy: `TLS13`
	2. Create a method
    	1. Method type: `GET`
    	2. Integration type: `Mock`
    	3. Authorization: `AWS IAM`
	3. Configure `Integration Response`
    	1. Template body: `{"info": "hello"}`
    	2. Make a test call
	4. Deploy API
    	1. Stage: `New stage`
    	2. Stage name: `stage1`
    	3. Open the Invoke URL in browser
2. Test
	1. Access without authentication: 
		1. Request: `curl https://keob89vjj6.execute-api.us-east-1.amazonaws.com/stage1`
		2. Response: `{"message":"Missing Authentication Token"}`
	2. Accees with authentication:
		1. Request: `awscurl --service execute-api https://keob89vjj6.execute-api.us-east-1.amazonaws.com/stage1`
		2. Response: `{"info": "hello"}`

## Cleanup
1. Delete the API

## History
- 2026-01-05 success
