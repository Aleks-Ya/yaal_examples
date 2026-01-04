# 030-integration-lambda-query-parameters

## Task
Create a **REST** API that invokes a Lambda function which accepts **query** parameters.

## Steps
1. Create a Lambda function
    1. Type: `Author from scratch`
    2. Function name: `kata-query-parameters-rest-api-function`
    3. Runtime: `Python`
    4. Define handler:
       ```python
        def lambda_handler(event, context):
            return { 'Event': event }
       ```
    5. Test the function
2. Creat an API:
    1. Build a REST API:
        1. API name: `kata-query-paramets-lambda-rest-api`
    2. Create a method:
        1. Method type: `ANY`
        2. Integration type: Lambda function
        3. Lambda function: `kata-query-parameters-rest-api-function`
    3. Configure the method:
        1. Integration request: 
            1. Mapping template:
                1. Content type: `application/json`
                2. Template body: `{"aaa":"$input.params('aa')"}`
    4. Test from "Test" tab on the method page:
        1. Method type: `GET`
        2. Query strings: `aa=a1`
6. Deploy the API:
    1. New stage: `stage1`
    2. Test from CLI: `curl https://j2k8ps4hf1.execute-api.us-east-1.amazonaws.com/stage1?aa=b1`
    
## Cleanup
1. Delete the API
2. Delete the Function

## History
