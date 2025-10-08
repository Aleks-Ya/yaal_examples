# 020-caddy-to-fargate

## Task
Status: unfinished
Deploy NGinx server as a ECS Service on Fargate.

## Setup
1. Configure ECS
	1. Create an ECS Cluster
		1. Cluster name: `kata-cluster-caddy-to-fargate`
		2. Infrastructure:
			1. Select a method of obtaining compute capacity: `Fargate only`
	2. Create a Task Execution Role
		1. Trusted entity type: `AWS service`
		2. Service or use case: `Elastic Container Service`
		3. Use case: `Task Execution Role for Elastic Container Service`
		4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
		5. Role name: `kata-role-caddy-to-fargate-task`
	3. Create a Task Definition
		1. Task definition family: `kata-task-caddy-to-fargate`
		2. Infrastructure requirements:
			1. Launch type: 
				1. AWS Fargate: `enabled`
				2. Managed Instances: `disabled`
				3. Amazon EC2 instances: `disabled`
			2. Task size:
				1. CPU: `.5 vCPU`
				2. Memory: `1 GB`
			3. Task execution role: `kata-role-caddy-to-fargate-task`
		3. Container 1:
			1. Name: `caddy-container`
			2. Image URI: `caddy`
			3. Docker configuration:
				1. Command: `caddy,respond,-l,:80,-s,201,-b,BodyVersion1`
	4. Create a Load Balancer
		1. Create a Target Group
			1. Choose a target type: `IP addresses`
			2. Target group name: `kata-tg-caddy-to-fargate`
			3. VPC: `default`
			4. IP addresses: remove all
		2. Create a ABL
			1. Load balancer name: `kata-alb-caddy-to-fargate`
			2. VPC: `default`
			3. Availability Zones and subnets: select all
			4. Security groups: `default`
			5. Listeners and routing
				1. Listener HTTP:80
					1. Target Group: `kata-tg-caddy-to-fargate`
	5. Create a Service
		1. Task definition family: `kata-task-caddy-to-fargate`
		2. Task definition revision: `1`
		2. Service name: `kata-service-caddy-to-fargate`
		2. Compute options: `Launch type`
		3. Load balancing
			1. VPC: `default`
			2. Load balancer type: `Application Load Balancer`
			3. Use an existing load balancer: `kata-alb-caddy-to-fargate`
			4. Use an existing listener: `HTTP:80`
			5. Use an existing target group: `kata-tg-caddy-to-fargate`
	6. Verify: 
		1. Find DNS name of the Service
		2. `curl kata-alb-caddy-to-fargate-1190762649.us-east-1.elb.amazonaws.com` (expected body `BodyVersion1`)
2. Create a CodeDeploy Application
	1. Create a Service Role
		1. Trusted entity type: `AWS Service`
		2. Service or use case: `CodeDeploy`
		3. Use case: `CodeDeploy - ECS`
		4. Permissions policies: `AWSCodeDeployRoleForECS`
		5. Role name: `kata-role-caddy-to-fargate-service`
	2. Create an Application:
		1. Application name: `kata-app-caddy-to-fargate`
		2. Compute platform: `Amazon ECS`
	3. Create a Deployment Group:
		1. Enter a deployment group name: `kata-dg-caddy-to-fargate`
		2. Enter a service role: `kata-role-caddy-to-fargate-service`
		3. Choose an ECS cluster name: `kata-cluster-caddy-to-fargate`
		4. Choose an ECS service name: `kata-service-caddy-to-fargate`
		5. Choose a load balancer: `kata-alb-caddy-to-fargate`
		6. Production listener port: `HTTP:80`
		7. Test listener port: empty
		8. Target group 1 name: `kata-tg-caddy-to-fargate`
		9. Target group 2 name: `kata-tg-caddy-to-fargate`
	4. Create a Deployment
		1. Application: `kata-app-caddy-to-fargate`
		2. Deployment Group: `kata-dg-caddy-to-fargate`
		3. 

## Cleanup
1. Delete the Cluster
	1. Delete the Service (force)
	2. Delete the Cluster
2. Deregister all versions of the Task Definition `kata-task-caddy-to-fargate`
3. Delete Roles
	1. Delete Role `kata-role-caddy-to-fargate-task`
	2. Delete Role `kata-role-caddy-to-fargate-service`
4. Delete CloudWatch Log Group `/ecs/kata-task-caddy-to-fargate`
5. Delete Load Balancer `kata-alb-caddy-to-fargate`
6. Delete Target Group `kata-tg-caddy-to-fargate`

## History
