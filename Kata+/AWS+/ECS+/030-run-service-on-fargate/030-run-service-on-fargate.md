# 030-run-service-on-fargate

## Task
Run a web-server as an ECS Service on Fargate.

## Setup
1. Create a Cluster
	1. Cluster name: `kata-run-service-on-fargate-cluster`
	2. Infrastructure:
		1. `AWS Fargate` marked
		2. `Amazon EC2 instances` unmarked
2. Create a Task Definition
	1. Task definition family: `kata-run-service-on-fargate-task`
	2. Infrastructure requirements:
		1. Launch type: 
			1. `AWS Fargate` marked
			2. `Amazon EC2 instances` unmarked
		2. Task size:
			1. CPU: `.5 vCPU`
			2. Memory: `1 GB`
		3. Task execution role: `Create new role`
	3. Container 1:
		1. Name: `nginx-container`
		2. Image URI: `nginx`
3. Create a Service
	1. Service name: `kata-run-service-on-fargate-task-service`
4. Verify: 
	1. Find public IP in the task in the Service 
	2. Open in browser: http://3.92.183.12 (expected `Welcome to nginx!`)

## Cleanup
1. Delete the Service (cannot delete entier Cluster because it will fail as there are active tasks)
2. Delete the Cluster
2. Deregister the Task Definition
3. Delete the Instance Role `ecsTaskExecutionRole`
4. Delete CloudWatch Log Group `/ecs/kata-run-service-on-fargate-task`
