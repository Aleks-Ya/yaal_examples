# 100-integration-http

## Task
Create a **REST** API with an HTTP integration.

## Steps
1. Build a REST API
    1. Type: `New API`
    2. Name: `kata-api-integration-http`
    3. API endpoint type: `Regional`
2. Create a method
    1. Method type: `GET`
    2. Integration type: `HTTP`
    3. HTTP proxy integration: true
    4. HTTP method: `GET`
    5. Endpoint URL: `https://httpbin.io/uuid`
3. Make a test call
4. Deploy API
    1. Stage: `New stage`
    2. Stage name: `stage1`
    3. Open the Invoke URL in browser

## Cleanup
1. Delete the API

## History
- 2026-02-22 success
