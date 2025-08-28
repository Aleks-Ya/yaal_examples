# 010-api-gateway-mock-integration

## Task

Create a REST API with a Mock integration.

## Setup

1. Build a REST API
    1. Type: `New API`
    2. Name: `api-1`
    3. API endpoint type: `Regional`
2. Create a method
    1. Method type: `GET`
    2. Integration type: `Mock`
3. Configure `Integration Response`
    1. Template body: `{"info": "hello"}`
    2. Make a test call
4. Deploy API
    1. Stage: `stage1`
    2. Invoke stage URL with `curl`

## Cleanup

1. Delete the API
