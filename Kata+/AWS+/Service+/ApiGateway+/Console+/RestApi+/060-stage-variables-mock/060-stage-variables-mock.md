# 060-stage-variables-mock

## Task
Create a REST API with two Stages utilizing Stage Variables to configure a Mock Integration.

## Steps
1. Create a REST API
    1. Build a REST API
        1. Type: `New API`
        2. Name: `kata-api-stage-variables-mock`
        3. API endpoint type: `Regional`
    2. Create a method
        1. Method type: `GET`
        2. Integration type: `Mock`
    3. Configure `Integration Response`
        1. Template body: `{"info": "$stageVariables.person"}`
        2. Make a test call
    4. Deploy API
        1. Stage: `New stage`
        2. Stage name: `stage1`
    5. Add a Stage Variable: 
        1. Name: `person`
        2. Value: `John`
    6. Open the Invoke URL of `stage1` in browser
2. Create the 2nd Stage
	1. Create Stage
		1. Stage name: `stage2`
		2. Deployment: latest
    2. Add a Stage Variable: 
        1. Name: `person`
        2. Value: `Mary`
    3. Open the Invoke URL of `stage2` in browser

## Cleanup
1. Delete API: `kata-api-stage-variables-mock`

## History
- 2025-10-24 success
