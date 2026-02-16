# 030-run-service-on-fargate

## Task
Run a web-server as an ECS Service on Fargate.

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-run-service-on-fargate`
	2. Infrastructure: `Fargate only`
2. Create a Task Definition
	1. Create a Task Execution Role (workaround for stuck `Create new role` button)
		1. Trusted entity type: `AWS service`
		2. Service or use case: `Elastic Container Service`
		3. Use case: `Task Execution Role for Elastic Container Service`
		4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
		5. Role name: `kata-role-run-service-on-fargate`
	2. Create a Task Definition:
		1. Task definition family: `kata-task-run-service-on-fargate`
		2. Infrastructure requirements:
			1. Launch type: 
				1. `AWS Fargate`: marked
				2. `Managed Instances`: unmarked
				3. `Amazon EC2 instances`: unmarked
			2. Task size:
				1. CPU: `.5 vCPU`
				2. Memory: `1 GB`
			3. Task role: `-`
			4. Task execution role: `kata-role-run-service-on-fargate`
		3. Container 1:
			1. Name: `nginx-container`
			2. Image URI: `nginx`
3. Create a Service
	1. Click "Deploy" - "Create service" on the Task Definition
		1. Service name: `kata-service-run-service-on-fargate`
		2. Existing cluster: `kata-cluster-run-service-on-fargate`
		3. Compute options: `Capacity provider strategy`
4. Verify: 
	1. Find public IP in the Task in the Service 
	2. Open in browser: http://3.92.183.12 (expected `Welcome to nginx!`)

## Cleanup
1. Delete Service (force): `kata-service-run-service-on-fargate`
2. Delete the Cluster: `kata-cluster-run-service-on-fargate`
3. Deregister versions of the Task Definition: `kata-task-run-service-on-fargate`
4. Delete the Task Execution Role `kata-role-run-service-on-fargate`
5. Delete CloudWatch Log Group `/ecs/kata-task-run-service-on-fargate`

## History
- 2025-10-08 success
- 2026-02-16 success
