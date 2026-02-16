# 010-run-task-on-fargate

## Task
Run the Hello-World Docker container on Fargate.

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-run-task-on-fargate`
	2. Infrastructure: `Fargate only`
2. Create a Task Definition
	1. Create the Task Execution Role (workaround for stuck `Create new role` button)
		1. Create an IAM role:
			1. Trusted entity type: `AWS service`
			2. Service or use case: `Elastic Container Service`
			3. Use case: `Task Execution Role for Elastic Container Service`
			4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
			5. Role name: `kata-role-run-task-on-fargate`
	2. Create a Task Definition:
		1. Task definition family: `kata-task-run-task-on-fargate`
		2. Infrastructure requirements:
			1. Launch type: 
				1. `AWS Fargate`: marked
				2. `Managed Instances`: unmarked
				3. `Amazon EC2 instances`: unmarked
			2. Task size:
				1. CPU: `.5 vCPU`
				2. Memory: `1 GB`
			3. Task role: `-`
			4. Task execution role: `kata-role-run-task-on-fargate`
		3. Container 1:
			1. Name: `hello-container`
			2. Image URI: `hello-world`
3. Run the Task
	1. Compute options: `Launch type`
	2. Launch type: `FARGATE`
4. Verify: Task's logs should contain `Hello from Docker!`

## Cleanup
1. Deregister the version of the Task Definition
2. Delete the Cluster `kata-cluster-run-task-on-fargate`
3. Delete the Task Execution Role `kata-role-run-task-on-fargate`
4. Delete CloudWatch Log Group `/ecs/kata-task-run-task-on-fargate`

## History
- 2026-01-19 success
- 2026-02-16 success
