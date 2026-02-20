set -x

export ACCOUNT=523633434047
export ROLE_NAME=kata-role-logs
export ROLE_ARN=arn:aws:iam::$ACCOUNT:role/$ROLE_NAME
export FUNCTION=kata-f-logs
export POLICY=arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
export LOG_GROUP=/aws/lambda/$FUNCTION
