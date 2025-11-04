# 03-use-job-task-parameters

## Task
Use job parameters and task parameters.

## Prerequsites
1. A Workspace is created.

## Steps
1. Create an All-purpose compute
	1. Name: `Alex Ya's Cluster`
	2. Nodes: `Single node`
	3. Use Photon Acceleration: `off`
	4. Node type: `m4.large`
	5. Terminate after minutes of inactivity: `20`
2. Create a Notebook
	1. Name: `notebook-1`
	2. Cluster: `Alex Ya's Cluster`
	3. Cmd 1:
	```python
	jobParam1 = dbutils.widgets.get("jobParam1")
	taskParam1 = dbutils.widgets.get("taskParam1")
	jobParamOverwrittenByTaskParam1 = dbutils.widgets.get("jobParamOverwrittenByTaskParam1")

	print(f'jobParam1: {jobParam1}')
	print(f'taskParam1: {taskParam1}')
	print(f'jobParamOverwrittenByTaskParam1: {jobParamOverwrittenByTaskParam1}')

	assert jobParam1 == 'jobParam1_value'
	assert taskParam1 == 'taskParam1_value'
	assert jobParamOverwrittenByTaskParam1 == 'jobParamOverwrittenByTaskParam1_value_job'
	```
3. Create a job
	1. Create a task
		1. Name: `job-parameter-task-1`
		2. Type: `Notebook`
		3. Source: `Workspace`
		4. Path: `/Users/alex_ya@mailbox.org/notebook-1`
		5. Cluster: `Alex Ya's Cluster`
		6. Parameters:
			1. `taskParam1` = `taskParam1_value`
			2. `jobParamOverwrittenByTaskParam1` = `jobParamOverwrittenByTaskParam1_value_task`
	2. Job parameters:
		1. `jobParam1` = `jobParam1_value`
		2. `jobParamOverwrittenByTaskParam1` = `jobParamOverwrittenByTaskParam1_value_job`
4. Test: run job

## Cleanup
1. Delete Job
2. Delete Notebook
3. Delete Compute
