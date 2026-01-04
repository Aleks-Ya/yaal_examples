# 080-usage-plan-quota

## Task
Limit number of requests to a REST API using quota in a Usage Plan.

## Steps
1. Create an API
	1. Build a REST API
    	1. Type: `New API`
    	2. Name: `kata-api-usage-plan-quota`
    	3. API endpoint type: `Regional`
    	4. Security policy: `TLS13`
	2. Create a method
    	1. Method type: `GET`
    	2. Integration type: `Mock`
    	3. API key required: true
	3. Configure `Integration Response`
    	1. Template body: `{"info": "hello"}`
    	2. Make a test call
	4. Deploy API
    	1. Stage: `New stage`
    	2. Stage name: `stage1`
    	3. Open the Invoke URL in browser
2. Create a Usage Plan
	1. Create a Usage Plan
		1. Name: `kata-up-usage-plan-quota`
		2. Throttling: disabled
		3. Quota: 2 requests per month
	2. Associate the Usage Plan with the Stage
3. Create an API Key
	1. Create an API Key:
		1. Name: `kata-key-usage-plan-quota`
		2. API key: `Auto generate`
	2. Associate the API Key with the Usage Plan
4. Test
	1. Prepare command: 
		```shell
		curl -H "X-API-Key: mDYm5fzHMGan4rCK31MBTEPE5LFRGkx2XotK6yfg" \
			https://ss1gix8hqf.execute-api.us-east-1.amazonaws.com/stage1
		```
	2. Send request #1: response `{"info": "hello"}`
	3. Send request #2: response `{"info": "hello"}`
	4. Send request #3: response `{"message":"Limit Exceeded"}`

## Cleanup
1. Delete the API
2. Delete the API Key
3. Delete the Usage Plan

## History
- 2026-01-05 success
