# 050-task-exceeds-cluster-capacity

## Task
Create a cluster that can accommodate only one task.
Create 2 tasks: the 1st has enough capacity to start, the 2nd doesn't. 

## Steps
1. Create a Cluster
	1. Cluster name: `kata-cluster-task-exceeds-cluster-capacity`
	2. Infrastructure: only `AWS Fargate` marked
		1. `AWS Fargate` unmarked
		2. `Amazon EC2 instances` marked
			1. EC2 instance type: `c1.medium`
			2. EC2 instance role: `Create new role`
			3. Desired capacity:
				1. Minimum: `0`
				2. Maximum: `2`
2. Create a Task Definition
	1. Task definition family: `kata-task-task-exceeds-cluster-capacity`
	2. Infrastructure requirements:
		1. Launch type: 
			1. `AWS Fargate` unmarked
			2. `Amazon EC2 instances` marked
		2. Network mode: `host`
		3. Task size:
			1. CPU: `1 vCPU`
			2. Memory: `1 GB`
	3. Container 1:
		1. Name: `nginx-container`
		2. Image URI: `nginx`
3. Run the Task
	1. Desired tasks: `3`
	2. Compute options: `Capacity provider strategy`
4. Verify: only 2 tasks in the cluster have `Running` state and 1 task has `Pending` state
5. Stop one of running tasks
6. Verify: the `Pending` task becase `Running`

## Cleanup
1. Delete the Cluster
2. Deregister the Task Definition
3. Delete the Instance Role `ecsTaskExecutionRole`
4. Delete CloudWatch Log Group `/ecs/kata-task-task-exceeds-cluster-capacity`

## History
