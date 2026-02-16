# 020-run-task-on-ec2-instance

## Task
Run the Hello-World Docker container on an EC2 instance.

## Steps
1. Create a Cluster
	1. Create a Role for the Instance Profile:
		1. Trusted entity type: `AWS service`
		2. Service or use case: `Elastic Container Service`
		3. Use case: `EC2 Role for Elastic Container Service`
		4. Permissions policies: `AmazonEC2ContainerServiceforEC2Role`
		5. Role name: `kata-role-run-task-on-ec2-instance-profile`
	2. Create a Cluster
		1. Cluster name: `kata-cluster-run-task-on-ec2-instance`
		2. Infrastructure: 
			1. Select a method of obtaining compute capacity: `Fargate and Self-managed instances`
			2. Auto Scaling group (ASG): `Create a new Auto Scaling group`
			3. Provisioning model: `On-demand`
			4. EC2 instance type: `c1.medium`
			5. EC2 instance role: `kata-role-run-task-on-ec2-instance-profile`
			6. Desired capacity:
				1. Minimum: `0`
				2. Maximum: `2`
2. Create a Task Definition
	1. Create the Task Execution Role (workaround for stuck `Create new role` button):
		1. Trusted entity type: `AWS service`
		2. Service or use case: `Elastic Container Service`
		3. Use case: `Task Execution Role for Elastic Container Service`
		4. Permissions policies: `AmazonECSTaskExecutionRolePolicy`
		5. Role name: `kata-role-run-task-on-ec2-instance-task`
	2. Create a Task Definition:
		1. Task definition family: `kata-task-run-task-on-ec2-instance`
		2. Infrastructure requirements:
			1. Launch type: 
				1. `AWS Fargate` unmarked
				2. `Managed Instances` unmarked
				3. `Amazon EC2 instances` marked
			2. Task size:
				1. CPU: `1 vCPU`
				2. Memory: `1 GB`
			3. Task role: `-`
			4. Task execution role: `kata-role-run-task-on-ec2-instance-task`
		3. Container 1:
			1. Name: `hello-container`
			2. Image URI: `hello-world`
3. Run the Task
	1. Desired tasks: `1`
	2. Compute options: `Capacity provider strategy`
	3. Capacity provider strategy: `Use cluster default`
4. Verify: 
	1. EC2 Instances for the ASG were run
	2. Task's logs should contain `Hello from Docker!`

## Cleanup
1. Delete the Cluster: `kata-cluster-run-task-on-ec2-instance`
2. Deregister all versions of the Task Definition: `kata-task-run-task-on-ec2-instance`
3. Delete the Instance Profile Role: `kata-role-run-task-on-ec2-instance-profile`
4. Delete the Task Execution Role: `kata-role-run-task-on-ec2-instance-task`
5. Delete CloudWatch Log Group `/ecs/kata-task-run-task-on-ec2-instance`

## History
- 2026-02-17 success
