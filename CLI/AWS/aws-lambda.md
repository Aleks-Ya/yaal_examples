# AWS lambda CLI

List Lambda Functions: `aws lambda list-functions`
List versions of a Function: `aws lambda list-versions-by-function --function-name function1`
Upload ZIP to a Function:`aws lambda update-function-code --function-name function1 --zip-file fileb://function1.zip --publish`
Show Function configuration: `aws lambda get-function-configuration --function-name function1`
Change Function Handler: `aws lambda update-function-configuration --function-name function1 --handler example.Handler::handleRequest`
Change Function Timeout in sec (900 sec is max): `aws lambda update-function-configuration --function-name function1 --timeout 900`
Delete Function: `aws lambda delete-function --function-name function1`

## Invoke
Redirect output to console: ``aws lambda invoke --function-name function1 /dev/stdout``
Invoke a Function without payload: `aws lambda invoke --function-name function1 response.json`

Invoke a Function with payload:
1. Encode payload: `PAYLOAD=$(echo -n '{"field1":"value1"}' | base64)`
2. Invoke the function: `aws lambda invoke --function-name function1 --payload $PAYLOAD response.json`
3. View the response: `cat response.json`

Invoke a Function asynchronously (no payload): `aws lambda invoke --function-name function1 --invocation-type Event response.json`
