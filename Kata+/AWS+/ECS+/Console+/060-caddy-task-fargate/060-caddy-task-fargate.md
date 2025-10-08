# 060-caddy-task-fargate

## Task
Status: success
Run Task with a Caddy web-server on Fargate.

## Setup
1. Cluster name: `kata-cluster-caddy-task-fargate`
	2. Infrastructure:
		1. Select a method of obtaining compute capacity: `Fargate only`
2. Create a Task Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `Elastic Container Service`
	3. Use case: `Task Execution Role for Elastic Container Service`
	4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
	5. Role name: `kata-role-caddy-task-fargate`
3. Create a Task Definition
	1. Task definition family: `kata-task-caddy-task-fargate`
	2. Infrastructure requirements:
		1. Launch type: 
			1. AWS Fargate: `enabled`
			2. Managed Instances: `disabled`
			3. Amazon EC2 instances: `disabled`
		2. Task size:
			1. CPU: `.5 vCPU`
			2. Memory: `1 GB`
		3. Task execution role: `kata-role-caddy-task-fargate`
	3. Container 1:
		1. Name: `caddy-container`
		2. Image URI: `caddy`
		3. Docker configuration:
			1. Command: `caddy,respond,-l,:80,-s,201,-b,Body1,-H,Header1: Value1`
4. Run the Task
	1. Compute options: `Launch type`
5. Verify: 
	1. Find public IP in the Task
	2. `curl -i 3.92.183.12` (expected status 201, body `Body1`, header `Header1` with value `Value1`)

## Cleanup
1. Delete the Cluster
	1. Stop the Task
	2. Delete the Cluster
2. Deregister all versions of the Task Definition
3. Delete the Instance Role `kata-role-caddy-task-fargate`
4. Delete CloudWatch Log Group `/ecs/kata-task-caddy-task-fargate`

## History
- 2025-10-08 success
