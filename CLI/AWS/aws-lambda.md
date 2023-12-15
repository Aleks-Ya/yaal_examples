# AWS lambda CLI

List Lambda Functions: `aws lambda list-functions`
List versions of a Function: `aws lambda list-versions-by-function --function-name my-function`
Upload ZIP to a Function:`aws lambda update-function-code --function-name my-function --zip-file fileb://my-function.zip --publish`
Show Function configuration: `aws lambda get-function-configuration --function-name my-function`
Change Function Handler: `aws lambda update-function-configuration --function-name my-function --handler example.Handler::handleRequest`
Change Function Timeout in sec (900 sec is max): `aws lambda update-function-configuration --function-name my-function --timeout 900`

Invoke a Function with payload:
1. Encode payload: `PAYLOAD=$(echo -n '{"field1":"value1"}' | base64)`
2. Invoke the function: `aws lambda invoke --function-name my-function --payload $PAYLOAD response.json`
3. View the response: `cat response.json`

Invoke a Function asynchronously (no payload): `aws lambda invoke --function-name function-1 --invocation-type Event response.json`
