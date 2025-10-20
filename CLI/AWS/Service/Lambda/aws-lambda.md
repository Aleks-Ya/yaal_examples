# AWS lambda CLI

## Function (LATEST version)
List Functions: `aws lambda list-functions`
Upload ZIP to a Function: `aws lambda update-function-code --function-name function1 --zip-file fileb://function1.zip --publish`
Show Function details: `aws lambda get-function --function-name function1`
Show Function configuration: `aws lambda get-function-configuration --function-name function1`
Change Function Handler: `aws lambda update-function-configuration --function-name function1 --handler example.Handler::handleRequest`
Change Function Timeout in sec (900 sec is max): `aws lambda update-function-configuration --function-name function1 --timeout 900`
Delete Function: `aws lambda delete-function --function-name function1`
Create a Function:
```shell
aws lambda create-function \
  --function-name function2 \
  --runtime python3.13 \
  --role arn:aws:iam::123456789012:role/lambda-ex \
  --handler handler.lambda_handler \
  --zip-file fileb://function2.zip
```
Download Function code: `wget -O code.zip $(aws lambda get-function --query 'Code.Location' --output text --function-name function1)`

## Version
List versions of a Function: `aws lambda list-versions-by-function --function-name function1`
Show Function Version details: `aws lambda get-function --function-name function1:3`

## Runtime
List available Runtimes: `aws lambda create-function help` (see `--runtime` section)

## Invoke
Redirect output to console: `aws lambda invoke --function-name function1 /dev/stdout`
Invoke a Function without payload: `aws lambda invoke --function-name function1 response.json`

Invoke a Function with payload:
1. Encode payload: `PAYLOAD=$(echo -n '{"field1":"value1"}' | base64)`
2. Invoke the function: `aws lambda invoke --function-name function1 --payload $PAYLOAD response.json`
3. View the response: `cat response.json`

Invoke a Function asynchronously (no payload): `aws lambda invoke --function-name function1 --invocation-type Event response.json`

## Function URL
Create (no auth): `aws lambda create-function-url-config --function-name function1 --auth-type NONE`

## Function Policy
Show function resource-based policy: `aws lambda get-policy --function-name function1`
Add permission to a function: 
```shell
aws lambda add-permission \
  --function-name function1 \
  --statement-id Invoke \
  --action "lambda:InvokeFunction" \
  --principal "*"
```
Remove a permission from a function: `aws lambda remove-permission --function-name function1 --statement-id Invoke`

## Layer
List layers: `aws lambda list-layers`
Create a layer: `aws lambda publish-layer-version --layer-name my-layer --compatible-runtimes python3.13 --zip-file fileb://layer.zip`
Update layers in a function:
```shell
aws lambda update-function-configuration \
  --function-name my-function \
  --layers arn:aws:lambda:us-east-1:123456789012:layer:my-layer:2
```
Delete a Layer (each version one-by-one): `aws lambda delete-layer-version --layer-name my-layer --version-number 1`
