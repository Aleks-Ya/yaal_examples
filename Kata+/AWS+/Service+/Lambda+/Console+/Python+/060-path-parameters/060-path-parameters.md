# 060-path-parameters

## Task
Extract path parameters from HTTP request query string within a Lambda Function.

## Steps
1. Create a function:
    1. Template: `Author from scratch`
    2. Function name: `kata-f-path-parameters`
    3. Runtime: `Python`
    4. Enable function URL:
        1. Auth type: `NONE`
2. Configure the Function
    1. Define handler:
       ```python
        def lambda_handler(event, context):
        	raw_path = event.get('rawPath', '')
            params = raw_path.split('/')
            non_empty_params = [param for param in params if param.strip() != '']
            return {
                'Event': event,
                'All parameters': params, 
                'Not empty parameter': non_empty_params
            }
       ```
    2. Deploy the function
3. Test
    1. Construct URL: append `aaa/bbb/ccc` to the Function URL
    2. Send request: 
        ```shell
        curl -s "https://swawdhqnyns3724nhpr5flhkkm0fwcsz.lambda-url.us-east-1.on.aws/aaa/bbb/ccc" \
        | jq .
        ```

## Cleanup
1. Delete function `kata-f-path-parameters`
2. Delete Role `kata-f-path-parameters-role-`

## History
