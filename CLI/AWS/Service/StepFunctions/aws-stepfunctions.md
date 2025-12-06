# StepFunctions CLI

## State Machine
List state machines: `aws stepfunctions list-state-machines`
Delete a State Machine:
```shell
aws stepfunctions delete-state-machine \
	--state-machine-arn arn:aws:states:us-east-1:523633434047:stateMachine:kata-sm-pass-flow
```

## Execution
Start a State Machine: 
```shell
aws stepfunctions start-execution \
	--state-machine-arn arn:aws:states:us-east-1:523633434047:stateMachine:kata-sm-pass-flow
```
List executions of a State Machine:
```shell
aws stepfunctions list-executions \
	--state-machine-arn arn:aws:states:us-east-1:523633434047:stateMachine:kata-sm-pass-flow
```
Show details of an Execution:
```shell
aws stepfunctions describe-execution \
	--execution-arn arn:aws:states:us-east-1:523633434047:execution:kata-sm-pass-flow:execution1
```
