# 020-lambda-origin

## Task
Status: ?
Create a CloudFront Distribution with a Lambda function origin.

## Steps
1. Create a Lambda Function
    1. Type: `Author from scratch`
    2. Name: `function-1`
    3. Runtime: `Python`
    4. Enable function URL:
        1. Auth type: `NONE`
2. Create a Distribution
    1. Origin domain: Function URL `https://znw2dp3ekjeb7opi4bucgwl2rq0rajyr.lambda-url.us-east-1.on.aws/`
    2. Web Application Firewall (WAF): `Do not enable security protections`
3. Test
    1. Invoke the `curl https://d3e4not5sumfv3.cloudfront.net/`

## Cleanup
1. Delete Distribution
2. Delete Function: `function-1`
