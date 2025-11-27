# 030-run-service-on-fargate

## Task
Status: success
Run a web-server as an ECS Service on Fargate.

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-run-service-on-fargate`
	2. Infrastructure:
		1. Select a method of obtaining compute capacity: `Fargate only`
2. Create a Task Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `Elastic Container Service`
	3. Use case: `Task Execution Role for Elastic Container Service`
	4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
	5. Role name: `kata-role-run-service-on-fargate`
3. Create a Task Definition
	1. Task definition family: `kata-task-run-service-on-fargate`
	2. Infrastructure requirements:
		1. Launch type: 
			1. AWS Fargate: `enabled`
			2. Managed Instances: `disabled`
			3. Amazon EC2 instances: `disabled`
		2. Task size:
			1. CPU: `.5 vCPU`
			2. Memory: `1 GB`
		3. Task execution role: `kata-role-run-service-on-fargate`
	3. Container 1:
		1. Name: `nginx-container`
		2. Image URI: `nginx`
4. Create a Service
	1. Service name: `kata-service-run-service-on-fargate`
5. Verify: 
	1. Find public IP in the Task in the Service 
	2. Open in browser: http://3.92.183.12 (expected `Welcome to nginx!`)

## Cleanup
1. Delete Service (force)
2. Delete the Cluster
3. Deregister versions of the Task Definition
4. Delete Role `kata-role-run-service-on-fargate`
5. Delete CloudWatch Log Group `/ecs/kata-task-run-service-on-fargate`

## History
- 2025-10-08 success
