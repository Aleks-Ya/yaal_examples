# 020-lambda-origin

## Task
Create a CloudFront Distribution with a Lambda function origin.

## Steps
1. Create a Lambda Function
    1. Type: `Author from scratch`
    2. Name: `kata-f-lambda-origin`
    3. Runtime: `Python`
    4. Execution role: `Create a new role with basic Lambda permissions`
    5. Function URL:
        1. Enable: true
        2. Auth type: `NONE`
2. Create a Distribution
    1. Choose a plan: `Pay as you go`
    2. Distribution name: `kata-distribution-lambda-origin`
    3. Origin type: `Other`
    4. Custom origin: domain only from Function URL `igg6r5lwfsibmd2oivupc3tsfy0xkuxl.lambda-url.us-east-1.on.aws`
3. Test
    1. Construct URL from Distribution domain name: `https://d4ns83lo1m66k.cloudfront.net`
    2. Open the URL in browser
    3. See `"Hello from Lambda!"`

## Cleanup
1. Delete Distribution
    1. Disable the Distribution
    2. Delete the Distribution
2. Delete Function: `kata-f-lambda-origin`
3. Delete Execution role: `kata-f-lambda-origin-role-`

## History
- 2025-01-15 success
