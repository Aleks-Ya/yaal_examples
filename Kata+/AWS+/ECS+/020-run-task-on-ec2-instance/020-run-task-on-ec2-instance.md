# 020-run-task-on-ec2-instance

## Task
Run the Hello-World Docker container on an EC2 instance.

## Setup
1. Create a Cluster
	1. Cluster name: `kata-run-task-on-ec2-instance-cluster`
	2. Infrastructure: only `AWS Fargate` marked
		1. `AWS Fargate` unmarked
		2. `Amazon EC2 instances` marked
			1. EC2 instance type: `c1.medium`
			2. EC2 instance role: `Create new role`
			3. Desired capacity:
				1. Minimum: `0`
				2. Maximum: `2`
2. Create a Task Definition
	1. Task definition family: `kata-run-task-on-ec2-instance-task`
	2. Infrastructure requirements:
		1. Launch type: 
			1. `AWS Fargate` unmarked
			2. `Amazon EC2 instances` marked
		2. Task size:
			1. CPU: `1 vCPU`
			2. Memory: `1 GB`
	3. Container 1:
		1. Name: `hello-container`
		2. Image URI: `hello-world`
3. Run the Task
	1. Compute options: `Capacity provider strategy`
4. Verify: Task's logs should contain `Hello from Docker!`

## Cleanup
1. Delete the Cluster
2. Deregister the Task Definition
3. Delete the Instance Role `ecsTaskExecutionRole`
4. Delete CloudWatch Log Group `/ecs/kata-run-task-on-ec2-instance-task`
