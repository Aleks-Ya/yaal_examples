# 010-invoke-function-locally

## Task
Use SAM to create a new Lambda Function and invoke it locally.

## Steps
1. Set environment variables
   ```shell
   set -x
   export DIR=/tmp/kata-invoke-function-locally
   export APP=hello-app
   ```
2. Initialize an Application:
	```shell
	sam init --runtime python3.13 --app-template hello-world -o $DIR --name $APP \
		--no-tracing --no-application-insights --no-structured-logging
	```
3. Change dir: `cd $DIR/$APP`
4. Build the application: `sam build`
5. Invoke locally: `echo '{}' | sam local invoke HelloWorldFunction`
6. Test by curl:
	1. Start an API: `sam local start-api`
	2. Test (in a new terminal): `curl -i http://127.0.0.1:3000/hello`

## Cleanup
1. Stop the API (`Ctrl-C`)
2. Delete dir: `rm -rf $DIR`
3. Unset env vars: `set +x; unset DIR APP`

## History
- 2025-11-28 success
