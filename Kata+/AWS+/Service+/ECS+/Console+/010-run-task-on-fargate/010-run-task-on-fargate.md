# 010-run-task-on-fargate

## Task
Run the Hello-World Docker container on Fargate.

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-run-task-on-fargate`
	2. Infrastructure:
		1. `AWS Fargate` marked
		2. `Amazon EC2 instances` unmarked
2. Create a Task Definition
	1. Task definition family: `kata-task-run-task-on-fargate`
	2. Infrastructure requirements:
		1. Launch type: 
			1. `AWS Fargate` marked
			2. `Amazon EC2 instances` unmarked
		2. Task size:
			1. CPU: `.5 vCPU`
			2. Memory: `1 GB`
		3. Task execution role: `Create new role`
	3. Container 1:
		1. Name: `hello-container`
		2. Image URI: `hello-world`
3. Run the Task
	1. Compute options: `Launch type`
4. Verify: Task's logs should contain `Hello from Docker!`

## Cleanup
1. Delete the Cluster
2. Deregister the Task Definition
3. Delete the Instance Role `ecsTaskExecutionRole`
4. Delete CloudWatch Log Group `/ecs/kata-task-run-task-on-fargate`
