# 070-update-service-fargate

## Task
Update a Service with a new revision of a Task.

## Steps
1. Cluster name: `kata-cluster-update-service-fargate`
	2. Infrastructure:
		1. Select a method of obtaining compute capacity: `Fargate only`
2. Create a Task Execution Role
	1. Trusted entity type: `AWS service`
	2. Service or use case: `Elastic Container Service`
	3. Use case: `Task Execution Role for Elastic Container Service`
	4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
	5. Role name: `kata-role-update-service-fargate`
3. Create a Task Definition
	1. Task definition family: `kata-task-update-service-fargate`
	2. Infrastructure requirements:
		1. Launch type: 
			1. AWS Fargate: `enabled`
			2. Managed Instances: `disabled`
			3. Amazon EC2 instances: `disabled`
		2. Task size:
			1. CPU: `.5 vCPU`
			2. Memory: `1 GB`
		3. Task execution role: `kata-role-update-service-fargate`
	3. Container 1:
		1. Name: `caddy-container`
		2. Image URI: `caddy`
		3. Docker configuration:
			1. Command: `caddy,respond,-l,:80,-b,BodyVersion1`
4. Create a Load Balancer
	1. Create a Target Group
		1. Choose a target type: `IP addresses`
		2. Target group name: `kata-tg-update-service-fargate`
		3. VPC: `default`
		4. IP addresses: remove all
	2. Create a ABL
		1. Load balancer name: `kata-alb-update-service-fargate`
		2. VPC: `default`
		3. Availability Zones and subnets: select all
		4. Security groups: `default`
		5. Listeners and routing
			1. Listener HTTP:80
				1. Target Group: `kata-tg-update-service-fargate`
5. Create a Service
	1. Task definition family: `kata-task-update-service-fargate`
	2. Task definition revision: `1`
	3. Service name: `kata-service-update-service-fargate`
	4. Compute options: `Launch type`
	5. Load balancing
		1. VPC: `default`
		2. Load balancer type: `Application Load Balancer`
		3. Use an existing load balancer: `kata-alb-update-service-fargate`
		4. Use an existing listener: `HTTP:80`
		5. Use an existing target group: `kata-tg-update-service-fargate`
6. Verify: 
	1. Find DNS name of the Service
	2. `curl kata-alb-update-service-fargate-2103972644.us-east-1.elb.amazonaws.com` (expected body `BodyVersion1`)
7. Create new revision of the Task
	1. Container 1:
		1. Docker configuration:
			1. Command: `caddy,respond,-l,:80,-b,BodyVersion2`
8. Update Service
	1. Task definition family: `kata-task-update-service-fargate`
	2. Task definition revision: `2`
9. Verify: `curl kata-alb-update-service-fargate-2103972644.us-east-1.elb.amazonaws.com` (expected body `BodyVersion2`)

## Cleanup
1. Delete the Cluster
	1. Delete the Service (force)
	2. Delete the Cluster
2. Deregister all versions of the Task Definition `kata-task-update-service-fargate`
3. Delete the Instance Role `kata-role-update-service-fargate`
4. Delete CloudWatch Log Group `/ecs/kata-task-update-service-fargate`
5. Delete Load Balancer `kata-alb-update-service-fargate`
6. Delete Target Group `kata-tg-update-service-fargate`

## History
- 2025-10-09 success
