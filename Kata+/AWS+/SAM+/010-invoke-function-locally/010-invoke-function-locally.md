# 010-invoke-function-locally

## Task
Use SAM to create a new Lambda Function and invoke it locally.

## Steps
1. Open a new terminal
2. Set environment variables
   ```shell
   set -x
   export DIR=/tmp/kata-invoke-function-locally
   export APP=hello-app
   ```
3. Initialize an Application:
	```shell
	sam init --runtime python3.14 --app-template hello-world -o $DIR --name $APP \
		--no-tracing --no-application-insights --no-structured-logging
	```
4. Change dir: `cd $DIR/$APP`
5. Build the application: `sam build`
6. Invoke locally: `echo '{}' | sam local invoke HelloWorldFunction`
7. Test by curl:
	1. Start an API: `sam local start-api`
	2. Test (in a new terminal): `curl -i http://127.0.0.1:3000/hello`

## Cleanup
1. Stop the API (`Ctrl-C`)
2. Delete dir: `rm -rf $DIR`
3. Close the terminal

## History
- 2025-11-28 success
