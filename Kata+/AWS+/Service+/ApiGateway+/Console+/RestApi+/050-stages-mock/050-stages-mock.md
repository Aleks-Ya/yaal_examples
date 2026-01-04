# 050-stages-mock

## Task
Create a REST API with two Stages pointing to different versions of a Mock integration.

## Steps
1. Create a REST API
	1. Build a REST API
    	1. Type: `New API`
    	2. Name: `kata-api-stages-mock`
    	3. API endpoint type: `Regional`
	2. Create a method
    	1. Method type: `GET`
    	2. Integration type: `Mock`
	3. Configure `Integration Response`
    	1. Template body: `{"info": "body1"}`
    	2. Make a test call
	4. Deploy API
    	1. Stage: `New stage`
    	2. Stage name: `stage1`
    	3. Open the Invoke URL in browser
2. Create the 2nd Stage
	1. Create Stage
		1. Stage name: `stage2`
		2. Deployment: latest
	2. Test stage: `curl https://041f3eslx5.execute-api.us-east-1.amazonaws.com/stage2/`
3. Modify response for `stage2`
	1. Edit resource `/GET`
		1. Configure `Integration Response`
    		1. Template body: `{"info": "body2"}`
    		2. Make a test call
    2. Deploy API
		1. Stage: `stage2`
    	2. Test stage 2: `curl https://041f3eslx5.execute-api.us-east-1.amazonaws.com/stage2/`
    	3. Test stage 1: `curl https://041f3eslx5.execute-api.us-east-1.amazonaws.com/stage1/`


## Cleanup
1. Delete API: `kata-api-stages-mock`

## History
- 2025-10-24 success
