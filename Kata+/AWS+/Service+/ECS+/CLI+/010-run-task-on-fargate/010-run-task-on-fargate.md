# 010-run-task-on-fargate

## Task
Run the Hello-World Docker container on Fargate.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export ACCOUNT=523633434047
	export CLUSTER=kata-cluster-run-task-on-fargate
	export FAMILY=kata-family-run-task-on-fargate
	```
3. Create a cluster: `aws ecs create-cluster --cluster-name $CLUSTER`
4. Register a Task Definition:
	```shell
	aws ecs register-task-definition \
		--family $FAMILY \
		--memory 512 \
		--cpu 256 \
		--requires-compatibilities FARGATE \
		--network-mode awsvpc \
		--container-definitions file://container-definitions.json
	```
4. Run the Task: 
```shell
aws ecs run-task --cluster $CLUSTER --launch-type FARGATE --task-definition $FAMILY:1 \
	--network-configuration "awsvpcConfiguration={
		subnets=[subnet-abc12345],
		securityGroups=[sg-abc12345],
		assignPublicIp=ENABLED
	}"
	```

## Cleanup
3. Close the terminal

## History
