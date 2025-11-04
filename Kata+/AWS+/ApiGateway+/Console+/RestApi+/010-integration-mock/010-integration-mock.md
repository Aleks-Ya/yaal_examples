# 010-integration-mock

## Task
Status: success
Create a REST API with a Mock integration.

## Steps
1. Build a REST API
    1. Type: `New API`
    2. Name: `kata-api-integration-mock`
    3. API endpoint type: `Regional`
2. Create a method
    1. Method type: `GET`
    2. Integration type: `Mock`
3. Configure `Integration Response`
    1. Template body: `{"info": "hello"}`
    2. Make a test call
4. Deploy API
    1. Stage: `New stage`
    2. Stage name: `stage1`
    3. Open the Invoke URL in browser

## Cleanup
1. Delete the API

## History
- 2025-10-14 success
