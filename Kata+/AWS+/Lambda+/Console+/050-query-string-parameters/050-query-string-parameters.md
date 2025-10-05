# 050-query-string-parameters

## Task
Status: ?
Extract parameters from HTTP request query string within a Lambda Function.

## Setup
1. Create a function:
    1. Template: `Author from scratch`
    2. Function name: `kata-query-string-parameters-function`
    3. Runtime: `Python`
    4. Enable function URL:
        1. Auth type: `NONE`
2. Configure the Function
    1. Define handler:
       ```python
        def lambda_handler(event, context):
            params = event.get('queryStringParameters', {})
            name_param = params.get('name')
            age_param = params.get('age')
            absent_param = params.get('absent')
            return {
                'Event': event,
                'All Query String Parameters': params, 
                'Name Parameter': name_param,
                'Age Parameter': age_param,
                'Absent Parameter': absent_param
            }
       ```
    2. Deploy the function
3. Test
    1. Construct URL: append `?name=John&age=30` to the Function URL
    2. Send request: `curl -s "https://swawdhqnyns3724nhpr5flhkkm0fwcsz.lambda-url.us-east-1.on.aws/?name=John&age=30" | jq .`

## Cleanup
1. Delete function `kata-query-string-parameters-function`
