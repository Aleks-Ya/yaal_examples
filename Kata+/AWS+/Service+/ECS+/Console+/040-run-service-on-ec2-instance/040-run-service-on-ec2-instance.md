# 040-run-service-on-ec2-instance

## Task
Run a web-server as an ECS Service on an EC2 instance.

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-run-service-on-ec2-instance`
	2. Infrastructure: only `AWS Fargate` marked
		1. `AWS Fargate` unmarked
		2. `Amazon EC2 instances` marked
			1. EC2 instance type: `c1.medium`
			2. EC2 instance role: `Create new role`
			3. Desired capacity:
				1. Minimum: `0`
				2. Maximum: `2`
2. Create a Task Definition
	1. Task definition family: `kata-task-run-service-on-ec2-instance`
	2. Infrastructure requirements:
		1. Launch type: 
			1. `AWS Fargate` unmarked
			2. `Amazon EC2 instances` marked
		2. Network mode: `host`
		3. Task size:
			1. CPU: `1 vCPU`
			2. Memory: `1 GB`
		4. Task execution role: `Create new role`
	3. Container 1:
		1. Name: `nginx-container`
		2. Image URI: `nginx`
3. Create a Service
	1. Service name: `kata-service-run-service-on-ec2-instance`
4. Verify: 
	1. Find public IP in the task in the Service 
	2. Open in browser: http://3.92.183.12 (expected `Welcome to nginx!`)

## Cleanup
1. Delete the Service
	1. Scale down to 0 tasks ("Update" service)
	2. Delete the Service
2. Delete the Cluster
3. Deregister the Task Definition
4. Delete the Instance Role `ecsTaskExecutionRole`
5. Delete CloudWatch Log Group `/ecs/kata-task-run-service-on-ec2-instance`

## History
